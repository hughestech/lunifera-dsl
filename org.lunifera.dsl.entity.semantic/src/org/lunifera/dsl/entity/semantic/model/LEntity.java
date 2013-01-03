/**
 * Copyright (c) 2011 - 2012, Florian Pirchner - lunifera.org
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Sources generated by Xtext  
 * 
 * Contributors:
 *  		Hans Georg Glöckler - Initial implementation
 *  		Florian Pirchner - Initial implementation
 */
package org.lunifera.dsl.entity.semantic.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>LEntity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#isEmbeddable <em>Embeddable</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#getEntityMembers <em>Entity Members</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LEntity#isCachable <em>Cachable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity()
 * @model
 * @generated
 */
public interface LEntity extends LType {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link org.lunifera.dsl.entity.semantic.model.LAnnotationDef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<LAnnotationDef> getAnnotations();

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_Abstract()
	 * @model
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LEntity#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Cachable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cachable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cachable</em>' attribute.
	 * @see #setCachable(boolean)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_Cachable()
	 * @model
	 * @generated
	 */
	boolean isCachable();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LEntity#isCachable <em>Cachable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cachable</em>' attribute.
	 * @see #isCachable()
	 * @generated
	 */
	void setCachable(boolean value);

	/**
	 * Returns the value of the '<em><b>Embeddable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embeddable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embeddable</em>' attribute.
	 * @see #setEmbeddable(boolean)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_Embeddable()
	 * @model
	 * @generated
	 */
	boolean isEmbeddable();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LEntity#isEmbeddable <em>Embeddable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Embeddable</em>' attribute.
	 * @see #isEmbeddable()
	 * @generated
	 */
	void setEmbeddable(boolean value);

	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' reference.
	 * @see #setSuperType(LEntity)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_SuperType()
	 * @model
	 * @generated
	 */
	LEntity getSuperType();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LEntity#getSuperType <em>Super Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' reference.
	 * @see #getSuperType()
	 * @generated
	 */
	void setSuperType(LEntity value);

	/**
	 * Returns the value of the '<em><b>Entity Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.lunifera.dsl.entity.semantic.model.LEntityMember}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Members</em>' containment reference list.
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLEntity_EntityMembers()
	 * @model containment="true"
	 * @generated
	 */
	EList<LEntityMember> getEntityMembers();

} // LEntity
