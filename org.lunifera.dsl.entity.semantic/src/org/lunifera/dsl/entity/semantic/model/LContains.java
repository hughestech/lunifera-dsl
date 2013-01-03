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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>LContains</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LContains#isNotnull <em>Notnull</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LContains#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LContains#getOpposite <em>Opposite</em>}</li>
 *   <li>{@link org.lunifera.dsl.entity.semantic.model.LContains#getSingularName <em>Singular Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLContains()
 * @model
 * @generated
 */
public interface LContains extends LReference {
	/**
	 * Returns the value of the '<em><b>Notnull</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notnull</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notnull</em>' attribute.
	 * @see #setNotnull(boolean)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLContains_Notnull()
	 * @model
	 * @generated
	 */
	boolean isNotnull();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LContains#isNotnull <em>Notnull</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notnull</em>' attribute.
	 * @see #isNotnull()
	 * @generated
	 */
	void setNotnull(boolean value);

	/**
	 * Returns the value of the '<em><b>Multiplicity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiplicity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiplicity</em>' containment reference.
	 * @see #setMultiplicity(LMultiplicity)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLContains_Multiplicity()
	 * @model containment="true"
	 * @generated
	 */
	LMultiplicity getMultiplicity();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LContains#getMultiplicity <em>Multiplicity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiplicity</em>' containment reference.
	 * @see #getMultiplicity()
	 * @generated
	 */
	void setMultiplicity(LMultiplicity value);

	/**
	 * Returns the value of the '<em><b>Opposite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opposite</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite</em>' reference.
	 * @see #setOpposite(LContainer)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLContains_Opposite()
	 * @model
	 * @generated
	 */
	LContainer getOpposite();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LContains#getOpposite <em>Opposite</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opposite</em>' reference.
	 * @see #getOpposite()
	 * @generated
	 */
	void setOpposite(LContainer value);

	/**
	 * Returns the value of the '<em><b>Singular Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Singular Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Singular Name</em>' attribute.
	 * @see #setSingularName(String)
	 * @see org.lunifera.dsl.entity.semantic.model.EntityPackage#getLContains_SingularName()
	 * @model
	 * @generated
	 */
	String getSingularName();

	/**
	 * Sets the value of the '{@link org.lunifera.dsl.entity.semantic.model.LContains#getSingularName <em>Singular Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Singular Name</em>' attribute.
	 * @see #getSingularName()
	 * @generated
	 */
	void setSingularName(String value);

} // LContains
