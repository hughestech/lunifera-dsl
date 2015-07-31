/**
 * Copyright (c) 2011 - 2014, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 *  All rights reserved. This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html 
 * 
 *  Based on ideas from Xtext, Xtend, Xcore
 *    
 *  Contributors:  
 *  		Florian Pirchner - Initial implementation 
 *  
 */
package org.lunifera.dsl.semantic.entity.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.common.types.JvmTypeReference;

import org.lunifera.dsl.semantic.common.types.LDatatypeConstraint;
import org.lunifera.dsl.semantic.common.types.LKeyAndValue;
import org.lunifera.dsl.semantic.common.types.LReference;
import org.lunifera.dsl.semantic.common.types.LResultFilters;
import org.lunifera.dsl.semantic.common.types.LunTypesPackage;

import org.lunifera.dsl.semantic.entity.LEntity;
import org.lunifera.dsl.semantic.entity.LEntityReference;
import org.lunifera.dsl.semantic.entity.LunEntityPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>LEntity Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#isLazy <em>Lazy</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#isCascading <em>Cascading</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getTypeJvm <em>Type Jvm</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getOpposite <em>Opposite</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LEntityReferenceImpl#getResultFilters <em>Result Filters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LEntityReferenceImpl extends LEntityFeatureImpl implements LEntityReference {
	/**
	 * The default value of the '{@link #isLazy() <em>Lazy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazy()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAZY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLazy() <em>Lazy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazy()
	 * @generated
	 * @ordered
	 */
	protected boolean lazy = LAZY_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascading() <em>Cascading</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascading()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascading() <em>Cascading</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascading()
	 * @generated
	 * @ordered
	 */
	protected boolean cascading = CASCADING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<LKeyAndValue> properties;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<LDatatypeConstraint> constraints;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected LEntity type;

	/**
	 * The cached value of the '{@link #getTypeJvm() <em>Type Jvm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeJvm()
	 * @generated
	 * @ordered
	 */
	protected JvmTypeReference typeJvm;

	/**
	 * The cached value of the '{@link #getOpposite() <em>Opposite</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpposite()
	 * @generated
	 * @ordered
	 */
	protected LEntityReference opposite;

	/**
	 * The cached value of the '{@link #getResultFilters() <em>Result Filters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultFilters()
	 * @generated
	 * @ordered
	 */
	protected LResultFilters resultFilters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LEntityReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LunEntityPackage.Literals.LENTITY_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLazy() {
		return lazy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLazy(boolean newLazy) {
		boolean oldLazy = lazy;
		lazy = newLazy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__LAZY, oldLazy, lazy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCascading() {
		return cascading;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCascading(boolean newCascading) {
		boolean oldCascading = cascading;
		cascading = newCascading;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__CASCADING, oldCascading, cascading));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LKeyAndValue> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<LKeyAndValue>(LKeyAndValue.class, this, LunEntityPackage.LENTITY_REFERENCE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LDatatypeConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<LDatatypeConstraint>(LDatatypeConstraint.class, this, LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LEntity getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (LEntity)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LunEntityPackage.LENTITY_REFERENCE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LEntity basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(LEntity newType) {
		LEntity oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JvmTypeReference getTypeJvm() {
		return typeJvm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeJvm(JvmTypeReference newTypeJvm, NotificationChain msgs) {
		JvmTypeReference oldTypeJvm = typeJvm;
		typeJvm = newTypeJvm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM, oldTypeJvm, newTypeJvm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeJvm(JvmTypeReference newTypeJvm) {
		if (newTypeJvm != typeJvm) {
			NotificationChain msgs = null;
			if (typeJvm != null)
				msgs = ((InternalEObject)typeJvm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM, null, msgs);
			if (newTypeJvm != null)
				msgs = ((InternalEObject)newTypeJvm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM, null, msgs);
			msgs = basicSetTypeJvm(newTypeJvm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM, newTypeJvm, newTypeJvm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LEntityReference getOpposite() {
		if (opposite != null && opposite.eIsProxy()) {
			InternalEObject oldOpposite = (InternalEObject)opposite;
			opposite = (LEntityReference)eResolveProxy(oldOpposite);
			if (opposite != oldOpposite) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LunEntityPackage.LENTITY_REFERENCE__OPPOSITE, oldOpposite, opposite));
			}
		}
		return opposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LEntityReference basicGetOpposite() {
		return opposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpposite(LEntityReference newOpposite) {
		LEntityReference oldOpposite = opposite;
		opposite = newOpposite;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__OPPOSITE, oldOpposite, opposite));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LResultFilters getResultFilters() {
		return resultFilters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResultFilters(LResultFilters newResultFilters, NotificationChain msgs) {
		LResultFilters oldResultFilters = resultFilters;
		resultFilters = newResultFilters;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS, oldResultFilters, newResultFilters);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResultFilters(LResultFilters newResultFilters) {
		if (newResultFilters != resultFilters) {
			NotificationChain msgs = null;
			if (resultFilters != null)
				msgs = ((InternalEObject)resultFilters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS, null, msgs);
			if (newResultFilters != null)
				msgs = ((InternalEObject)newResultFilters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS, null, msgs);
			msgs = basicSetResultFilters(newResultFilters, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS, newResultFilters, newResultFilters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
			case LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM:
				return basicSetTypeJvm(null, msgs);
			case LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS:
				return basicSetResultFilters(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LunEntityPackage.LENTITY_REFERENCE__LAZY:
				return isLazy();
			case LunEntityPackage.LENTITY_REFERENCE__CASCADING:
				return isCascading();
			case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES:
				return getProperties();
			case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS:
				return getConstraints();
			case LunEntityPackage.LENTITY_REFERENCE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM:
				return getTypeJvm();
			case LunEntityPackage.LENTITY_REFERENCE__OPPOSITE:
				if (resolve) return getOpposite();
				return basicGetOpposite();
			case LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS:
				return getResultFilters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LunEntityPackage.LENTITY_REFERENCE__LAZY:
				setLazy((Boolean)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__CASCADING:
				setCascading((Boolean)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends LKeyAndValue>)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends LDatatypeConstraint>)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__TYPE:
				setType((LEntity)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM:
				setTypeJvm((JvmTypeReference)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__OPPOSITE:
				setOpposite((LEntityReference)newValue);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS:
				setResultFilters((LResultFilters)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LunEntityPackage.LENTITY_REFERENCE__LAZY:
				setLazy(LAZY_EDEFAULT);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__CASCADING:
				setCascading(CASCADING_EDEFAULT);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES:
				getProperties().clear();
				return;
			case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS:
				getConstraints().clear();
				return;
			case LunEntityPackage.LENTITY_REFERENCE__TYPE:
				setType((LEntity)null);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM:
				setTypeJvm((JvmTypeReference)null);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__OPPOSITE:
				setOpposite((LEntityReference)null);
				return;
			case LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS:
				setResultFilters((LResultFilters)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LunEntityPackage.LENTITY_REFERENCE__LAZY:
				return lazy != LAZY_EDEFAULT;
			case LunEntityPackage.LENTITY_REFERENCE__CASCADING:
				return cascading != CASCADING_EDEFAULT;
			case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case LunEntityPackage.LENTITY_REFERENCE__TYPE:
				return type != null;
			case LunEntityPackage.LENTITY_REFERENCE__TYPE_JVM:
				return typeJvm != null;
			case LunEntityPackage.LENTITY_REFERENCE__OPPOSITE:
				return opposite != null;
			case LunEntityPackage.LENTITY_REFERENCE__RESULT_FILTERS:
				return resultFilters != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == LReference.class) {
			switch (derivedFeatureID) {
				case LunEntityPackage.LENTITY_REFERENCE__LAZY: return LunTypesPackage.LREFERENCE__LAZY;
				case LunEntityPackage.LENTITY_REFERENCE__CASCADING: return LunTypesPackage.LREFERENCE__CASCADING;
				case LunEntityPackage.LENTITY_REFERENCE__PROPERTIES: return LunTypesPackage.LREFERENCE__PROPERTIES;
				case LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS: return LunTypesPackage.LREFERENCE__CONSTRAINTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == LReference.class) {
			switch (baseFeatureID) {
				case LunTypesPackage.LREFERENCE__LAZY: return LunEntityPackage.LENTITY_REFERENCE__LAZY;
				case LunTypesPackage.LREFERENCE__CASCADING: return LunEntityPackage.LENTITY_REFERENCE__CASCADING;
				case LunTypesPackage.LREFERENCE__PROPERTIES: return LunEntityPackage.LENTITY_REFERENCE__PROPERTIES;
				case LunTypesPackage.LREFERENCE__CONSTRAINTS: return LunEntityPackage.LENTITY_REFERENCE__CONSTRAINTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lazy: ");
		result.append(lazy);
		result.append(", cascading: ");
		result.append(cascading);
		result.append(')');
		return result.toString();
	}

} //LEntityReferenceImpl
