/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lunifera.dsl.dto.lib.services.jpa.metadata;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.lunifera.dsl.dto.lib.services.IQuery;
import org.lunifera.dsl.dto.lib.services.SortBy;
import org.lunifera.dsl.dto.lib.services.filters.ILFilter;
import org.lunifera.dsl.dto.lib.services.filters.LAdvancedFilterableSupport;
import org.lunifera.dsl.dto.lib.services.filters.LAnd;
import org.lunifera.dsl.dto.lib.services.filters.LCompare.Equal;
import org.lunifera.dsl.dto.lib.services.filters.LCompare.Greater;
import org.lunifera.dsl.dto.lib.services.filters.LCompare.Less;
import org.lunifera.dsl.dto.lib.services.filters.LOr;

/**
 * A read-only entity provider that works with a local {@link EntityManager}.
 * Most important features and limitations:
 * <ul>
 * <li>Does not do any internal caching, all information is always accessed
 * directly from the EntityManager</li>
 * <li>Explicitly detaches entities by default (see
 * {@link #isEntitiesDetached() })
 * <ul>
 * <li>Performs a serialize-deserialize cycle to clone entities in order to
 * explicitly detach them from the persistence context (<b>This is ugly!</b>).</li>
 * </ul>
 * </li>
 * <li>Uses lazy-loading of entities (when using detached entities, references
 * and collections within the entities should be configured to be fetched
 * eagerly, though)</li>
 * </ul>
 * 
 * This entity provider does not perform very well, as every method call results
 * in at least one query being sent to the entity manager. If speed is desired,
 * {@link CachingLocalEntityProvider} should be used instead. However, this
 * entity provider consumes less memory than the caching provider.
 * 
 * @author Petter Holmstr��m (Vaadin Ltd)
 * @since 1.0
 */
public class EntityDelegate<T> implements Serializable {

	private static final long serialVersionUID = 1601796410565144708L;
	private transient EntityManager entityManager;
	private EntityClassMetadata<T> entityClassMetadata;
	private boolean entitiesDetached = true;
	private Serializable serializableEntityManager;
	private QueryModifierDelegate queryModifierDelegate;
	private int maxResults;

	/**
	 * Creates a new <code>LocalEntityProvider</code>.
	 * 
	 * @param entityClass
	 *            the entity class (must not be null).
	 * @param entityManager
	 *            the entity manager to use (must not be null).
	 */
	public EntityDelegate(Class<T> entityClass, EntityManager entityManager,
			int maxResults) {
		this(entityClass);
		this.maxResults = maxResults;
		assert entityManager != null : "entityManager must not be null";
		setEntityManager(entityManager);
	}

	/**
	 * Creates a new <code>LocalEntityProvider</code>. The entity manager or an
	 * entity manager provider must be set using
	 * {@link #setEntityManager(javax.persistence.EntityManager)} or
	 * {@link #setEntityManagerProvider(com.vaadin.addon.jpacontainer.EntityManagerProvider)}
	 * respectively.
	 * 
	 * @param entityClass
	 *            the entity class (must not be null).
	 */
	public EntityDelegate(Class<T> entityClass) {
		assert entityClass != null : "entityClass must not be null";
		this.entityClassMetadata = MetadataFactory.getInstance()
				.getEntityClassMetadata(entityClass);
	}

	// TODO Test serialization of entity manager
	protected Object writeReplace() throws ObjectStreamException {
		if (entityManager != null && entityManager instanceof Serializable) {
			serializableEntityManager = (Serializable) entityManager;
		}
		return this;
	}

	protected Object readResolve() throws ObjectStreamException {
		if (serializableEntityManager != null) {
			this.entityManager = (EntityManager) serializableEntityManager;
		}
		return this;
	}

	/**
	 * Sets the entity manager.
	 * 
	 * @param entityManager
	 *            the entity manager to set.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Gets the metadata for the entity class.
	 * 
	 * @return the metadata (never null).
	 */
	protected EntityClassMetadata<T> getEntityClassMetadata() {
		return this.entityClassMetadata;
	}

	/**
	 * Gets the entity manager. If no entity manager has been set, the one
	 * returned by the registered entity manager provider is returned.
	 * 
	 * @return the entity manager.
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager (never null).
	 * @throws IllegalStateException
	 *             if no entity manager is set.
	 */
	protected EntityManager doGetEntityManager() throws IllegalStateException {
		if (getEntityManager() == null) {
			throw new IllegalStateException("No entity manager specified");
		}
		return getEntityManager();
	}

	/**
	 * Creates a copy of <code>original</code> and adds an entry for the primary
	 * key to the end of the list.
	 * 
	 * @param original
	 *            the original list of sorting instructions (must not be null,
	 *            but may be empty).
	 * @return a new list with the added entry for the primary key.
	 */
	protected List<SortBy> addPrimaryKeyToSortList(List<SortBy> original) {
		if (sortByListContainsPrimaryKey(original)) {
			return original;
		}
		ArrayList<SortBy> newList = new ArrayList<SortBy>();
		newList.addAll(original);
		if (getEntityClassMetadata().hasEmbeddedIdentifier()) {
			for (String p : getEntityClassMetadata().getIdentifierProperty()
					.getTypeMetadata().getPersistentPropertyNames()) {
				newList.add(new SortBy(getEntityClassMetadata()
						.getIdentifierProperty().getName() + "." + p, true));
			}
		} else {
			newList.add(new SortBy(getEntityClassMetadata()
					.getIdentifierProperty().getName(), true));
		}
		return Collections.unmodifiableList(newList);
	}

	/**
	 * @param original
	 * @return
	 */
	private boolean sortByListContainsPrimaryKey(List<SortBy> original) {
		for (SortBy sb : original) {
			EntityClassMetadata<T> metadata = getEntityClassMetadata();
			if (metadata.hasEmbeddedIdentifier()) {
				if (sb.getPropertyId()
						.equals(metadata.getIdentifierProperty()
								.getTypeMetadata().getPersistentPropertyNames()
								.iterator().next())) {
					return true;
				}
			} else {
				if (sb.getPropertyId().equals(
						metadata.getIdentifierProperty().getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Translates SortBy instances, which possibly contain nested properties
	 * (e.g. name.firstName, name.lastName) into Order instances which can be
	 * used in a CriteriaQuery.
	 * 
	 * @param sortBy
	 *            the SortBy instance to translate
	 * @param swapSortOrder
	 *            swaps the specified sort order if true.
	 * @param cb
	 *            the {@link CriteriaBuilder} to use
	 * @param root
	 *            the {@link CriteriaQuery} {@link Root} to be used.
	 * @return
	 */
	protected Order translateSortBy(SortBy sortBy, boolean swapSortOrder,
			CriteriaBuilder cb, Root<T> root) {
		String sortedPropId = sortBy.getPropertyId().toString();
		// First split the id and build a Path.
		String[] idStrings = sortedPropId.split("\\.");
		Path<T> path = null;
		if (idStrings.length > 1 && !isEmbedded(idStrings[0])) {
			// This is a nested property, we need to LEFT JOIN
			path = root.join(idStrings[0], JoinType.LEFT);
			for (int i = 1; i < idStrings.length; i++) {
				if (i < idStrings.length - 1) {
					path = ((Join<?, ?>) path)
							.join(idStrings[i], JoinType.LEFT);
				} else {
					path = path.get(idStrings[i]);
				}
			}
		} else {
			// non-nested or embedded, we can select as usual
			path = LAdvancedFilterableSupport.getPropertyPathTyped(root,
					sortedPropId);
		}

		// Make and return the Order instances.
		if (sortBy.isAscending() != swapSortOrder) {
			return cb.asc(path);
		} else {
			return cb.desc(path);
		}
	}

	/**
	 * @param propertyId
	 * @return
	 */
	private boolean isEmbedded(String propertyId) {
		return entityClassMetadata.getProperty(propertyId).getPropertyKind() == PropertyKind.EMBEDDED;
	}

	/**
	 * Creates a filtered query that does not do any sorting.
	 * 
	 * @see #createFilteredQuery(com.vaadin.addon.jpacontainer.EntityContainer,
	 *      java.util.List,
	 *      org.lunifera.dsl.dto.lib.services.filters.ILFilter.data.Container.IFilter,
	 *      java.util.List, boolean)
	 * @param fieldsToSelect
	 *            the fields to select (must not be null).
	 * @param filter
	 *            the filter to apply, or null if no filters should be applied.
	 * @return the query (never null).
	 */
	protected TypedQuery<Object> createUnsortedFilteredQuery(
			List<String> fieldsToSelect, ILFilter filter) {
		return createFilteredPropertyQuery(fieldsToSelect, filter, null, false);
	}

	/**
	 * Creates a filtered, optionally sorted, query.
	 * 
	 * @param fieldsToSelect
	 *            the fields to select (must not be null).
	 * @param filter
	 *            the filter to apply, or null if no filters should be applied.
	 * @param sortBy
	 *            the fields to sort by (must include at least one field), or
	 *            null if the result should not be sorted at all.
	 * @param swapSortOrder
	 *            true to swap the sort order, false to use the sort order
	 *            specified in <code>sortBy</code>. Only applies if
	 *            <code>sortBy</code> is not null.
	 * @return the query (never null).
	 */
	protected TypedQuery<Object> createFilteredPropertyQuery(
			List<String> fieldsToSelect, ILFilter filter, List<SortBy> sortBy,
			boolean swapSortOrder) {
		assert fieldsToSelect != null : "fieldsToSelect must not be null";
		assert sortBy == null || !sortBy.isEmpty() : "sortBy must be either null or non-empty";

		CriteriaBuilder cb = doGetEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> query = cb.createQuery();
		Root<T> root = query.from(entityClassMetadata.getMappedClass());

		tellDelegateQueryWillBeBuilt(cb, query);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter != null) {
			predicates.add(FilterConverter.convertFilter(filter, cb, root));
		}
		tellDelegateFiltersWillBeAdded(cb, query, predicates);
		if (!predicates.isEmpty()) {
			query.where(CollectionUtil.toArray(Predicate.class, predicates));
		}
		tellDelegateFiltersWereAdded(cb, query);

		List<Order> orderBy = new ArrayList<Order>();
		if (sortBy != null && sortBy.size() > 0) {
			for (SortBy sortedProperty : sortBy) {
				orderBy.add(translateSortBy(sortedProperty, swapSortOrder, cb,
						root));
			}
		}
		tellDelegateOrderByWillBeAdded(cb, query, orderBy);
		query.orderBy(orderBy);
		tellDelegateOrderByWereAdded(cb, query);

		if (fieldsToSelect.size() > 1
				|| getEntityClassMetadata().hasEmbeddedIdentifier()) {
			List<Path<?>> paths = new ArrayList<Path<?>>();
			for (String fieldPath : fieldsToSelect) {
				paths.add(LAdvancedFilterableSupport.getPropertyPathTyped(root,
						fieldPath));
			}
			query.multiselect(paths.toArray(new Path<?>[paths.size()]));
		} else {
			query.select(LAdvancedFilterableSupport.getPropertyPathTyped(root,
					fieldsToSelect.get(0)));
		}
		tellDelegateQueryHasBeenBuilt(cb, query);
		return doGetEntityManager().createQuery(query);
	}

	// /**
	// * Creates a filtered, optionally sorted, query.
	// *
	// * @param filter
	// * the filter to apply, or null if no filters should be applied.
	// * @param sortBy
	// * the fields to sort by (must include at least one field), or
	// * null if the result should not be sorted at all.
	// * @param swapSortOrder
	// * true to swap the sort order, false to use the sort order
	// * specified in <code>sortBy</code>. Only applies if
	// * <code>sortBy</code> is not null.
	// * @return the query (never null).
	// */
	// protected TypedQuery<Long> createFilteredCountQuery(ILFilter filter,
	// List<SortBy> sortBy, boolean swapSortOrder) {
	// assert sortBy == null || !sortBy.isEmpty() :
	// "sortBy must be either null or non-empty";
	//
	// CriteriaBuilder cb = doGetEntityManager().getCriteriaBuilder();
	// CriteriaQuery<Long> query = cb.createQuery(Long.class);
	// Root<T> root = query.from(entityClassMetadata.getMappedClass());
	//
	// tellDelegateQueryWillBeBuilt(cb, query);
	//
	// List<Predicate> predicates = new ArrayList<Predicate>();
	// if (filter != null) {
	// predicates.add(FilterConverter.convertFilter(filter, cb, root));
	// }
	// tellDelegateFiltersWillBeAdded(cb, query, predicates);
	// if (!predicates.isEmpty()) {
	// query.where(CollectionUtil.toArray(Predicate.class, predicates));
	// }
	// tellDelegateFiltersWereAdded(cb, query);
	//
	// // List<Order> orderBy = new ArrayList<Order>();
	// // if (sortBy != null && sortBy.size() > 0) {
	// // for (SortBy sortedProperty : sortBy) {
	// // orderBy.add(translateSortBy(sortedProperty, swapSortOrder, cb,
	// // root));
	// // }
	// // }
	// // tellDelegateOrderByWillBeAdded(cb, query, orderBy);
	// // query.orderBy(orderBy);
	// // tellDelegateOrderByWereAdded(cb, query);
	//
	// String entityIdPropertyName = getEntityClassMetadata()
	// .getIdentifierProperty().getName();
	// query.select(cb.count(root.get(entityIdPropertyName)));
	// // query.groupBy(root.get(entityIdPropertyName));
	//
	// tellDelegateQueryHasBeenBuilt(cb, query);
	// return doGetEntityManager().createQuery(query);
	// }

	/**
	 * Creates a filtered, optionally sorted, query.
	 * 
	 * @param filter
	 *            the filter to apply, or null if no filters should be applied.
	 * @param sortBy
	 *            the fields to sort by (must include at least one field), or
	 *            null if the result should not be sorted at all.
	 * @param swapSortOrder
	 *            true to swap the sort order, false to use the sort order
	 *            specified in <code>sortBy</code>. Only applies if
	 *            <code>sortBy</code> is not null.
	 * @return the query (never null).
	 */
	@SuppressWarnings("unchecked")
	protected TypedQuery<T> createFilteredEntityQuery(ILFilter filter,
			List<SortBy> sortBy, boolean swapSortOrder) {
		assert sortBy == null || !sortBy.isEmpty() : "sortBy must be either null or non-empty";

		CriteriaBuilder cb = doGetEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> query = cb.createQuery();
		Root<T> root = query.from(entityClassMetadata.getMappedClass());

		tellDelegateQueryWillBeBuilt(cb, query);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter != null) {
			predicates.add(FilterConverter.convertFilter(filter, cb, root));
		}
		tellDelegateFiltersWillBeAdded(cb, query, predicates);
		if (!predicates.isEmpty()) {
			query.where(CollectionUtil.toArray(Predicate.class, predicates));
		}
		tellDelegateFiltersWereAdded(cb, query);

		List<Order> orderBy = new ArrayList<Order>();
		if (sortBy != null && sortBy.size() > 0) {
			for (SortBy sortedProperty : sortBy) {
				orderBy.add(translateSortBy(sortedProperty, swapSortOrder, cb,
						root));
			}
		}
		tellDelegateOrderByWillBeAdded(cb, query, orderBy);
		query.orderBy(orderBy);
		tellDelegateOrderByWereAdded(cb, query);

		query.select(root);

		tellDelegateQueryHasBeenBuilt(cb, query);
		return (TypedQuery<T>) doGetEntityManager().createQuery(query);
	}

	protected boolean doContainsEntityIdentifier(Object entityId,
			ILFilter filter) {
		assert entityId != null : "entityId must not be null";
		String entityIdPropertyName = getEntityClassMetadata()
				.getIdentifierProperty().getName();

		CriteriaBuilder cb = doGetEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<T> root = query.from(getEntityClassMetadata().getMappedClass());

		tellDelegateQueryWillBeBuilt(cb, query);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(root.get(entityIdPropertyName),
				cb.literal(entityId)));
		if (filter != null) {
			predicates.add(FilterConverter.convertFilter(filter, cb, root));
		}
		tellDelegateFiltersWillBeAdded(cb, query, predicates);
		if (!predicates.isEmpty()) {
			query.where(CollectionUtil.toArray(Predicate.class, predicates));
		}
		tellDelegateFiltersWereAdded(cb, query);

		if (getEntityClassMetadata().hasEmbeddedIdentifier()) {
			/*
			 * Hibernate will generate SQL for "count(obj)" that does not run on
			 * HSQLDB. "count(*)" works fine, but then EclipseLink won't work.
			 * With this hack, this method should work with both Hibernate and
			 * EclipseLink.
			 */
			query.select(cb.count(root.get(entityIdPropertyName).get(
					getEntityClassMetadata().getIdentifierProperty()
							.getTypeMetadata().getPersistentPropertyNames()
							.iterator().next())));
		} else {

			query.select(cb.count(root.get(entityIdPropertyName)));
		}
		tellDelegateQueryHasBeenBuilt(cb, query);
		TypedQuery<Long> tq = doGetEntityManager().createQuery(query);
		return tq.getSingleResult() == 1;
	}

	public boolean containsEntityIdentifier(Object entityId, IQuery query) {
		return doContainsEntityIdentifier(entityId, query.getFilter());
	}

	protected T doGetEntity(Object entityId) {
		assert entityId != null : "entityId must not be null";
		T entity = doGetEntityManager().find(
				getEntityClassMetadata().getMappedClass(), entityId);
		return detachEntity(entity);
	}

	public T getEntity(Object entityId) {
		return doGetEntity(entityId);
	}

	protected Object doGetEntityIdentifierAt(ILFilter filter,
			List<SortBy> sortBy, int index) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		TypedQuery<Object> query = createFilteredPropertyQuery(
				Arrays.asList(getEntityClassMetadata().getIdentifierProperty()
						.getName()), filter, addPrimaryKeyToSortList(sortBy),
				false);
		query.setMaxResults(1);
		query.setFirstResult(index);
		List<?> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public Object getEntityIdentifierAt(IQuery query, int index) {
		return doGetEntityIdentifierAt(query.getFilter(), query.getSortOrder()
				.getSortBy(), index);
	}

	protected int doGetEntityCount(ILFilter filter) {
		String entityIdPropertyName = getEntityClassMetadata()
				.getIdentifierProperty().getName();

		CriteriaBuilder cb = doGetEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<T> root = query.from(getEntityClassMetadata().getMappedClass());

		tellDelegateQueryWillBeBuilt(cb, query);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter != null) {
			predicates.add(FilterConverter.convertFilter(filter, cb, root));
		}
		tellDelegateFiltersWillBeAdded(cb, query, predicates);
		if (!predicates.isEmpty()) {
			query.where(CollectionUtil.toArray(Predicate.class, predicates));
		}
		tellDelegateFiltersWereAdded(cb, query);

		if (getEntityClassMetadata().hasEmbeddedIdentifier()) {
			/*
			 * Hibernate will generate SQL for "count(obj)" that does not run on
			 * HSQLDB. "count(*)" works fine, but then EclipseLink won't work.
			 * With this hack, this method should work with both Hibernate and
			 * EclipseLink.
			 */

			query.select(cb.count(root.get(entityIdPropertyName).get(
					getEntityClassMetadata().getIdentifierProperty()
							.getTypeMetadata().getPersistentPropertyNames()
							.iterator().next())));
		} else {
			query.select(cb.count(root.get(entityIdPropertyName)));
		}
		tellDelegateQueryHasBeenBuilt(cb, query);
		TypedQuery<Long> tq = doGetEntityManager().createQuery(query);
		return tq.getSingleResult().intValue();
	}

	public int getEntityCount(IQuery query) {
		return doGetEntityCount(query.getFilter());
	}

	//
	// public int getEntityIndex(Object entityId, IQuery query) {
	// TypedQuery<Long> tQuery = createSiblingIndexQuery(entityId,
	// query.getFilter(), query.getSortOrder().getSortBy(), true);
	// return tQuery.getSingleResult().intValue();
	// }

	protected Object doGetFirstEntityIdentifier(ILFilter filter,
			List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}

		List<String> keyFields = Arrays.asList(getEntityClassMetadata()
				.getIdentifierProperty().getName());
		// if (getEntityClassMetadata().hasEmbeddedIdentifier()) {
		// keyFields = new ArrayList<String>();
		// for (String p : getEntityClassMetadata().getIdentifierProperty()
		// .getTypeMetadata().getPersistentPropertyNames()) {
		// keyFields.add(getEntityClassMetadata().getIdentifierProperty()
		// .getName() + "." + p);
		// }
		// }
		TypedQuery<Object> query = createFilteredPropertyQuery(keyFields,
				filter, addPrimaryKeyToSortList(sortBy), false);
		query.setMaxResults(1);
		List<?> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public Object getFirstEntityIdentifier(IQuery query) {
		return doGetFirstEntityIdentifier(query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected T doGetFirstEntity(ILFilter filter, List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}

		TypedQuery<T> query = createFilteredEntityQuery(filter,
				addPrimaryKeyToSortList(sortBy), false);
		query.setMaxResults(1);
		List<T> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public T getFirstEntity(IQuery query) {
		return doGetFirstEntity(query.getFilter(), query.getSortOrder()
				.getSortBy());
	}

	protected Object doGetLastEntityIdentifier(ILFilter filter,
			List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		// The last 'true' parameter switches the sort order -> the last row is
		// the first result.
		TypedQuery<Object> query = createFilteredPropertyQuery(
				Arrays.asList(getEntityClassMetadata().getIdentifierProperty()
						.getName()), filter, addPrimaryKeyToSortList(sortBy),
				true);
		query.setMaxResults(1);
		List<?> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public Object getLastEntityIdentifier(IQuery query) {
		return doGetLastEntityIdentifier(query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected T doGetLastEntity(ILFilter filter, List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		// The last 'true' parameter switches the sort order -> the last row is
		// the first result.
		TypedQuery<T> query = createFilteredEntityQuery(filter,
				addPrimaryKeyToSortList(sortBy), true);
		query.setMaxResults(1);
		List<T> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public T getLastEntity(IQuery query) {
		return doGetLastEntity(query.getFilter(), query.getSortOrder()
				.getSortBy());
	}

	/**
	 * If <code>backwards</code> is false, this method will return the
	 * identifier of the entity next to the entity identified by
	 * <code>entityId</code>. If true, this method will return the identifier of
	 * the entity previous to the entity identified by <code>entityId</code>.
	 * <code>filter</code> and <code>sortBy</code> is used to define and limit
	 * the list of entities to be used for determining the sibling.
	 * 
	 * @param entityId
	 *            the identifier of the entity whose sibling to retrieve (must
	 *            not be null).
	 * @param filter
	 *            an optional filter to limit the entities (may be null).
	 * @param sortBy
	 *            the order in which the list should be sorted (must not be
	 *            null).
	 * @param backwards
	 *            true to fetch the previous sibling, false to fetch the next
	 *            sibling.
	 * @return the identifier of the "sibling".
	 */
	protected Object getSibling(Object entityId, ILFilter filter,
			List<SortBy> sortBy, boolean backwards) {
		TypedQuery<Object> query = createSiblingQuery(entityId, filter, sortBy,
				backwards);
		query.setMaxResults(1);
		List<?> result = query.getResultList();
		if (result.size() != 1) {
			return null;
		} else {
			return result.get(0);
		}
	}

	/**
	 * This method creates a query that can be used to fetch the siblings of a
	 * specific entity. If <code>backwards</code> is false, the query will begin
	 * with the entity next to the entity identified by <code>entityId</code>.
	 * If <code>backwards</code> is false, the query will begin with the entity
	 * prior to the entity identified by <code>entityId</code>.
	 * 
	 * @param entityId
	 *            the identifier of the entity whose sibling to retrieve (must
	 *            not be null).
	 * @param filter
	 *            an optional filter to limit the entities (may be null).
	 * @param sortBy
	 *            the order in which the list should be sorted (must not be
	 *            null).
	 * @param backwards
	 *            true to fetch the previous sibling, false to fetch the next
	 *            sibling.
	 * @return the query that will return the sibling and all the subsequent
	 *         entities unless limited.
	 */
	protected TypedQuery<Object> createSiblingQuery(Object entityId,
			ILFilter filter, List<SortBy> sortBy, boolean backwards) {
		assert entityId != null : "entityId must not be null";
		assert sortBy != null : "sortBy must not be null";
		ILFilter limitingFilter;
		sortBy = addPrimaryKeyToSortList(sortBy);
		if (sortBy.size() == 1) {
			// The list is sorted by primary key
			if (backwards) {
				limitingFilter = new Less(getEntityClassMetadata()
						.getIdentifierProperty().getName(), entityId);
			} else {
				limitingFilter = new Greater(getEntityClassMetadata()
						.getIdentifierProperty().getName(), entityId);
			}
		} else {
			// We have to fetch the values of the sorted fields
			T currentEntity = getEntity(entityId);
			if (currentEntity == null) {
				throw new EntityNotFoundException(
						"No entity found with the ID " + entityId);
			}
			// Collect the values into a map for easy access
			Map<Object, Object> filterValues = new HashMap<Object, Object>();
			for (SortBy sb : sortBy) {
				filterValues.put(
						sb.getPropertyId(),
						getEntityClassMetadata().getPropertyValue(
								currentEntity, sb.getPropertyId().toString()));
			}
			// Now we can build a filter that limits the query to the entities
			// below entityId
			List<ILFilter> orFilters = new ArrayList<ILFilter>();
			for (int i = sortBy.size() - 1; i >= 0; i--) {
				// TODO Document this code snippet once it works
				// TODO What happens with null values?
				List<ILFilter> caseFilters = new ArrayList<ILFilter>();
				SortBy sb;
				for (int j = 0; j < i; j++) {
					sb = sortBy.get(j);
					caseFilters.add(new Equal(sb.getPropertyId(), filterValues
							.get(sb.getPropertyId())));
				}
				sb = sortBy.get(i);
				if (sb.isAscending() ^ backwards) {
					caseFilters.add(new Greater(sb.getPropertyId(),
							filterValues.get(sb.getPropertyId())));
				} else {
					caseFilters.add(new Less(sb.getPropertyId(), filterValues
							.get(sb.getPropertyId())));
				}
				orFilters.add(new LAnd(CollectionUtil.toArray(ILFilter.class,
						caseFilters)));
			}
			limitingFilter = new LOr(CollectionUtil.toArray(ILFilter.class,
					orFilters));
		}
		// Now, we can create the query
		ILFilter queryFilter;
		if (filter == null) {
			queryFilter = limitingFilter;
		} else {
			queryFilter = new LAnd(filter, limitingFilter);
		}
		TypedQuery<Object> query = createFilteredPropertyQuery(
				Arrays.asList(getEntityClassMetadata().getIdentifierProperty()
						.getName()), queryFilter, sortBy, backwards);
		return query;
	}

	// /**
	// * This method creates a query that can be used to fetch the siblings of a
	// * specific entity. If <code>backwards</code> is false, the query will
	// begin
	// * with the entity next to the entity identified by <code>entityId</code>.
	// * If <code>backwards</code> is false, the query will begin with the
	// entity
	// * prior to the entity identified by <code>entityId</code>.
	// *
	// * @param entityId
	// * the identifier of the entity whose sibling to retrieve (must
	// * not be null).
	// * @param filter
	// * an optional filter to limit the entities (may be null).
	// * @param sortBy
	// * the order in which the list should be sorted (must not be
	// * null).
	// * @param backwards
	// * true to fetch the previous sibling, false to fetch the next
	// * sibling.
	// * @return the query that will return the sibling and all the subsequent
	// * entities unless limited.
	// */
	// protected TypedQuery<Long> createSiblingIndexQuery(Object entityId,
	// ILFilter filter, List<SortBy> sortBy, boolean backwards) {
	// assert entityId != null : "entityId must not be null";
	// assert sortBy != null : "sortBy must not be null";
	// ILFilter limitingFilter;
	// sortBy = addPrimaryKeyToSortList(sortBy);
	// if (sortBy.size() == 1) {
	// // The list is sorted by primary key
	// if (backwards) {
	// limitingFilter = new Less(getEntityClassMetadata()
	// .getIdentifierProperty().getName(), entityId);
	// } else {
	// limitingFilter = new Greater(getEntityClassMetadata()
	// .getIdentifierProperty().getName(), entityId);
	// }
	// } else {
	// // We have to fetch the values of the sorted fields
	// T currentEntity = getEntity(entityId);
	// if (currentEntity == null) {
	// throw new EntityNotFoundException(
	// "No entity found with the ID " + entityId);
	// }
	// // Collect the values into a map for easy access
	// Map<Object, Object> filterValues = new HashMap<Object, Object>();
	// for (SortBy sb : sortBy) {
	// filterValues.put(
	// sb.getPropertyId(),
	// getEntityClassMetadata().getPropertyValue(
	// currentEntity, sb.getPropertyId().toString()));
	// }
	// // Now we can build a filter that limits the query to the entities
	// // below entityId
	// List<ILFilter> orFilters = new ArrayList<ILFilter>();
	// for (int i = sortBy.size() - 1; i >= 0; i--) {
	// // TODO Document this code snippet once it works
	// // TODO What happens with null values?
	// List<ILFilter> caseFilters = new ArrayList<ILFilter>();
	// SortBy sb;
	// for (int j = 0; j < i; j++) {
	// sb = sortBy.get(j);
	// caseFilters.add(new Equal(sb.getPropertyId(), filterValues
	// .get(sb.getPropertyId())));
	// }
	// sb = sortBy.get(i);
	// if (sb.isAscending() ^ backwards) {
	// caseFilters.add(new Greater(sb.getPropertyId(),
	// filterValues.get(sb.getPropertyId())));
	// } else {
	// caseFilters.add(new Less(sb.getPropertyId(), filterValues
	// .get(sb.getPropertyId())));
	// }
	// orFilters.add(new LAnd(CollectionUtil.toArray(ILFilter.class,
	// caseFilters)));
	// }
	// limitingFilter = new LOr(CollectionUtil.toArray(ILFilter.class,
	// orFilters));
	// }
	// // Now, we can create the query
	// ILFilter queryFilter;
	// if (filter == null) {
	// queryFilter = limitingFilter;
	// } else {
	// queryFilter = new LAnd(filter, limitingFilter);
	// }
	// TypedQuery<Long> query = createFilteredCountQuery(queryFilter, sortBy,
	// backwards);
	// return query;
	// }

	protected Object doGetNextEntityIdentifier(Object entityId,
			ILFilter filter, List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		return getSibling(entityId, filter, sortBy, false);
	}

	public Object getNextEntityIdentifier(Object entityId, IQuery query) {
		return doGetNextEntityIdentifier(entityId, query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected T doGetNextEntity(Object entityId, ILFilter filter,
			List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		Object resultId = getSibling(entityId, filter, sortBy, false);
		if (resultId != null) {
			return getEntity(resultId);
		}
		return null;
	}

	public T getNextEntity(Object entityId, IQuery query) {
		return doGetNextEntity(entityId, query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected Object doGetPreviousEntityIdentifier(Object entityId,
			ILFilter filter, List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		return getSibling(entityId, filter, sortBy, true);
	}

	public Object getPreviousEntityIdentifier(Object entityId, IQuery query) {
		return doGetPreviousEntityIdentifier(entityId, query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected T doGetPreviousEntity(Object entityId, ILFilter filter,
			List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		Object resultId = getSibling(entityId, filter, sortBy, true);
		if (resultId != null) {
			return getEntity(resultId);
		}
		return null;
	}

	public T getPreviousEntity(Object entityId, IQuery query) {
		return doGetPreviousEntity(entityId, query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	/**
	 * Detaches <code>entity</code> from the entity manager. If
	 * <code>entity</code> is null, then null is returned. If
	 * {@link #isEntitiesDetached() } is false, <code>entity</code> is returned
	 * directly.
	 * 
	 * @param entity
	 *            the entity to detach.
	 * @return the detached entity.
	 */
	protected T detachEntity(T entity) {
		if (entity == null) {
			return null;
		}
		if (isEntitiesDetached()) {
			getEntityManager().detach(entity);
		}
		return entity;
	}

	public boolean isEntitiesDetached() {
		return entitiesDetached;
	}

	public void setEntitiesDetached(boolean detached)
			throws UnsupportedOperationException {
		this.entitiesDetached = detached;
	}

	protected List<Object> doGetAllEntityIdentifiers(ILFilter filter,
			List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		sortBy = addPrimaryKeyToSortList(sortBy);
		TypedQuery<Object> query = createFilteredPropertyQuery(
				Arrays.asList(getEntityClassMetadata().getIdentifierProperty()
						.getName()), filter, sortBy, false);
		return Collections.unmodifiableList(query.getResultList());
	}

	public List<Object> getAllEntityIdentifiers(IQuery query) {
		return doGetAllEntityIdentifiers(query.getFilter(), query
				.getSortOrder().getSortBy());
	}

	protected List<T> doGetAllEntities(ILFilter filter, List<SortBy> sortBy) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		sortBy = addPrimaryKeyToSortList(sortBy);
		TypedQuery<T> query = createFilteredEntityQuery(filter, sortBy, false);
		query.setMaxResults(maxResults);
		return Collections.unmodifiableList(query.getResultList());
	}

	public List<T> getAllEntities(IQuery query) {
		return doGetAllEntities(query.getFilter(), query.getSortOrder()
				.getSortBy());
	}

	protected List<T> doGetAllEntities(ILFilter filter, List<SortBy> sortBy,
			int startIndex) {
		if (sortBy == null) {
			sortBy = Collections.emptyList();
		}
		sortBy = addPrimaryKeyToSortList(sortBy);
		TypedQuery<T> query = createFilteredEntityQuery(filter, sortBy, false);
		query.setFirstResult(startIndex);
		query.setMaxResults(maxResults);
		return Collections.unmodifiableList(query.getResultList());
	}

	public List<T> getAllEntities(IQuery query, int startIndex) {
		return doGetAllEntities(query.getFilter(), query.getSortOrder()
				.getSortBy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.addon.jpacontainer.EntityProvider#setQueryModifierDelegate
	 * (com.vaadin.addon.jpacontainer.EntityProvider.QueryModifierDelegate)
	 */
	public void setQueryModifierDelegate(QueryModifierDelegate delegate) {
		this.queryModifierDelegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.addon.jpacontainer.EntityProvider#getQueryModifierDelegate()
	 */
	public QueryModifierDelegate getQueryModifierDelegate() {
		return queryModifierDelegate;
	}

	// QueryModifierDelegate helper methods

	private void tellDelegateQueryWillBeBuilt(CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.queryWillBeBuilt(cb, query);
		}
	}

	private void tellDelegateQueryHasBeenBuilt(CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.queryHasBeenBuilt(cb, query);
		}
	}

	private void tellDelegateFiltersWillBeAdded(CriteriaBuilder cb,
			CriteriaQuery<?> query, List<Predicate> predicates) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.filtersWillBeAdded(cb, query, predicates);
		}
	}

	private void tellDelegateFiltersWereAdded(CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.filtersWereAdded(cb, query);
		}
	}

	private void tellDelegateOrderByWillBeAdded(CriteriaBuilder cb,
			CriteriaQuery<?> query, List<Order> orderBy) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.orderByWillBeAdded(cb, query, orderBy);
		}
	}

	private void tellDelegateOrderByWereAdded(CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		if (queryModifierDelegate != null) {
			queryModifierDelegate.orderByWasAdded(cb, query);
		}
	}

	public Object getIdentifier(T entity) {
		return entityClassMetadata.getPropertyValue(entity, entityClassMetadata
				.getIdentifierProperty().getName());
	}

	public T refreshEntity(T entity) {
		if (getEntityManager().contains(entity)) {
			try {
				getEntityManager().refresh(entity);
			} catch (IllegalArgumentException e) {
				// detached, removed or something, get by id from em and refresh
				// than non-detached object
				entity = findAndRefresh(entity);
			} catch (EntityNotFoundException e) {
				return null;
			} catch (TransactionRequiredException e) {
				// TODO: handle exception, only in transactional?
			}
		} else {
			entity = findAndRefresh(entity);
		}
		return entity;
	}

	private T findAndRefresh(T entity) {
		entity = getEntityManager().find(
				getEntityClassMetadata().getMappedClass(),
				getIdentifier(entity));
		if (entity != null) {
			try {
				// now try to refresh the attached entity
				getEntityManager().refresh(entity);
				entity = detachEntity(entity);
			} catch (TransactionRequiredException e) {
				// NOP
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return entity;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * com.vaadin.addon.jpacontainer.EntityContainer#setLazyLoadingDelegate(
	// * com.vaadin.addon.jpacontainer.EntityContainer.LazyLoadingDelegate)
	// */
	// public void setLazyLoadingDelegate(LazyLoadingDelegate delegate) {
	// lazyLoadingDelegate = delegate;
	// if (lazyLoadingDelegate != null) {
	// lazyLoadingDelegate.setEntityProvider(this);
	// }
	// }
	//
	// public LazyLoadingDelegate getLazyLoadingDelegate() {
	// return lazyLoadingDelegate;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.jpacontainer.EntityProvider#refresh()
	 */
	public void refresh() {
		// Nothing to do in this implementation, since we don't keep any
		// items/entities cached.
	}
}
