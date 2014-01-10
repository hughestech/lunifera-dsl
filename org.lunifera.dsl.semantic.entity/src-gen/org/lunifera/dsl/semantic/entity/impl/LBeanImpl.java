/**
 */
package org.lunifera.dsl.semantic.entity.impl;

import com.google.common.base.Objects;

import com.google.common.collect.Iterables;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import org.lunifera.dsl.semantic.common.types.LFeaturesHolder;
import org.lunifera.dsl.semantic.common.types.LScalarType;
import org.lunifera.dsl.semantic.common.types.LunTypesPackage;

import org.lunifera.dsl.semantic.common.types.impl.LClassImpl;

import org.lunifera.dsl.semantic.entity.EntityPackage;
import org.lunifera.dsl.semantic.entity.LBean;
import org.lunifera.dsl.semantic.entity.LBeanAttribute;
import org.lunifera.dsl.semantic.entity.LBeanFeature;
import org.lunifera.dsl.semantic.entity.LBeanReference;
import org.lunifera.dsl.semantic.entity.LOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>LBean</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LBeanImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LBeanImpl#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.lunifera.dsl.semantic.entity.impl.LBeanImpl#getSubTypes <em>Sub Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LBeanImpl extends LClassImpl implements LBean
{
  /**
   * The cached value of the '{@link #getFeatures() <em>Features</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFeatures()
   * @generated
   * @ordered
   */
  protected EList<LBeanFeature> features;

  /**
   * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperType()
   * @generated
   * @ordered
   */
  protected LBean superType;

  /**
   * The cached value of the '{@link #getSubTypes() <em>Sub Types</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubTypes()
   * @generated
   * @ordered
   */
  protected EList<LBean> subTypes;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LBeanImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EntityPackage.Literals.LBEAN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LBeanFeature> getFeatures()
  {
    if (features == null)
    {
      features = new EObjectContainmentEList<LBeanFeature>(LBeanFeature.class, this, EntityPackage.LBEAN__FEATURES);
    }
    return features;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LBean getSuperType()
  {
    return superType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSuperType(LBean newSuperType, NotificationChain msgs)
  {
    LBean oldSuperType = superType;
    superType = newSuperType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EntityPackage.LBEAN__SUPER_TYPE, oldSuperType, newSuperType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSuperType(LBean newSuperType)
  {
    if (newSuperType != superType)
    {
      NotificationChain msgs = null;
      if (superType != null)
        msgs = ((InternalEObject)superType).eInverseRemove(this, EntityPackage.LBEAN__SUB_TYPES, LBean.class, msgs);
      if (newSuperType != null)
        msgs = ((InternalEObject)newSuperType).eInverseAdd(this, EntityPackage.LBEAN__SUB_TYPES, LBean.class, msgs);
      msgs = basicSetSuperType(newSuperType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EntityPackage.LBEAN__SUPER_TYPE, newSuperType, newSuperType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LBean> getSubTypes()
  {
    if (subTypes == null)
    {
      subTypes = new EObjectWithInverseResolvingEList<LBean>(LBean.class, this, EntityPackage.LBEAN__SUB_TYPES, EntityPackage.LBEAN__SUPER_TYPE);
    }
    return subTypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LOperation> getOperations()
  {
    EList<LBeanFeature> _features = this.getFeatures();
    Iterable<LOperation> _filter = Iterables.<LOperation>filter(_features, LOperation.class);
    List<LOperation> _list = IterableExtensions.<LOperation>toList(_filter);
    return _list;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LBeanReference> getReferences()
  {
    EList<LBeanFeature> _features = this.getFeatures();
    Iterable<LBeanReference> _filter = Iterables.<LBeanReference>filter(_features, LBeanReference.class);
    List<LBeanReference> _list = IterableExtensions.<LBeanReference>toList(_filter);
    return _list;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LBeanAttribute> getAttributes()
  {
    EList<LBeanFeature> _features = this.getFeatures();
    Iterable<LBeanAttribute> _filter = Iterables.<LBeanAttribute>filter(_features, LBeanAttribute.class);
    List<LBeanAttribute> _list = IterableExtensions.<LBeanAttribute>toList(_filter);
    return _list;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LBeanFeature> getAllFeatures()
  {
    final List<LBeanFeature> result = CollectionLiterals.<LBeanFeature>newArrayList();
    this.collectAllLunFeatures(this, result);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LBeanAttribute> getAllAttributes()
  {
    final List<LBeanFeature> result = CollectionLiterals.<LBeanFeature>newArrayList();
    this.collectAllLunFeatures(this, result);
    Iterable<LBeanAttribute> _filter = Iterables.<LBeanAttribute>filter(result, LBeanAttribute.class);
    return IterableExtensions.<LBeanAttribute>toList(_filter);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<LBeanReference> getAllReferences()
  {
    final List<LBeanFeature> result = CollectionLiterals.<LBeanFeature>newArrayList();
    this.collectAllLunFeatures(this, result);
    Iterable<LBeanReference> _filter = Iterables.<LBeanReference>filter(result, LBeanReference.class);
    return IterableExtensions.<LBeanReference>toList(_filter);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void collectAllLunFeatures(final LBean current, final List<LBeanFeature> result)
  {
    boolean _equals = Objects.equal(current, null);
    if (_equals)
    {
      return;
    }
    EList<LBeanFeature> _features = current.getFeatures();
    result.addAll(_features);
    LBean _superType = current.getSuperType();
    this.collectAllLunFeatures(_superType, result);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__SUPER_TYPE:
        if (superType != null)
          msgs = ((InternalEObject)superType).eInverseRemove(this, EntityPackage.LBEAN__SUB_TYPES, LBean.class, msgs);
        return basicSetSuperType((LBean)otherEnd, msgs);
      case EntityPackage.LBEAN__SUB_TYPES:
        return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubTypes()).basicAdd(otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__FEATURES:
        return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
      case EntityPackage.LBEAN__SUPER_TYPE:
        return basicSetSuperType(null, msgs);
      case EntityPackage.LBEAN__SUB_TYPES:
        return ((InternalEList<?>)getSubTypes()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__FEATURES:
        return getFeatures();
      case EntityPackage.LBEAN__SUPER_TYPE:
        return getSuperType();
      case EntityPackage.LBEAN__SUB_TYPES:
        return getSubTypes();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__FEATURES:
        getFeatures().clear();
        getFeatures().addAll((Collection<? extends LBeanFeature>)newValue);
        return;
      case EntityPackage.LBEAN__SUPER_TYPE:
        setSuperType((LBean)newValue);
        return;
      case EntityPackage.LBEAN__SUB_TYPES:
        getSubTypes().clear();
        getSubTypes().addAll((Collection<? extends LBean>)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__FEATURES:
        getFeatures().clear();
        return;
      case EntityPackage.LBEAN__SUPER_TYPE:
        setSuperType((LBean)null);
        return;
      case EntityPackage.LBEAN__SUB_TYPES:
        getSubTypes().clear();
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EntityPackage.LBEAN__FEATURES:
        return features != null && !features.isEmpty();
      case EntityPackage.LBEAN__SUPER_TYPE:
        return superType != null;
      case EntityPackage.LBEAN__SUB_TYPES:
        return subTypes != null && !subTypes.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedOperationID(int baseOperationID, Class<?> baseClass)
  {
    if (baseClass == LScalarType.class)
    {
      switch (baseOperationID)
      {
        default: return -1;
      }
    }
    if (baseClass == LFeaturesHolder.class)
    {
      switch (baseOperationID)
      {
        case LunTypesPackage.LFEATURES_HOLDER___GET_FEATURES: return EntityPackage.LBEAN___GET_FEATURES;
        case LunTypesPackage.LFEATURES_HOLDER___GET_ALL_FEATURES: return EntityPackage.LBEAN___GET_ALL_FEATURES;
        default: return -1;
      }
    }
    return super.eDerivedOperationID(baseOperationID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
  {
    switch (operationID)
    {
      case EntityPackage.LBEAN___GET_OPERATIONS:
        return getOperations();
      case EntityPackage.LBEAN___GET_REFERENCES:
        return getReferences();
      case EntityPackage.LBEAN___GET_ATTRIBUTES:
        return getAttributes();
      case EntityPackage.LBEAN___GET_ALL_FEATURES:
        return getAllFeatures();
      case EntityPackage.LBEAN___GET_ALL_ATTRIBUTES:
        return getAllAttributes();
      case EntityPackage.LBEAN___GET_ALL_REFERENCES:
        return getAllReferences();
      case EntityPackage.LBEAN___COLLECT_ALL_LUN_FEATURES__LBEAN_LIST:
        collectAllLunFeatures((LBean)arguments.get(0), (List<LBeanFeature>)arguments.get(1));
        return null;
    }
    return super.eInvoke(operationID, arguments);
  }

} //LBeanImpl