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
package org.lunifera.dsl.semantic.common.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>LTyped Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.dsl.semantic.common.types.LTypedPackage#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.dsl.semantic.common.types.LunTypesPackage#getLTypedPackage()
 * @model
 * @generated
 */
public interface LTypedPackage extends LPackage {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.lunifera.dsl.semantic.common.types.LType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.lunifera.dsl.semantic.common.types.LunTypesPackage#getLTypedPackage_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<LType> getTypes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.ecore.resource.Resource%> resource = this.eResource();\nboolean _equals = <%com.google.common.base.Objects%>.equal(resource, null);\nif (_equals)\n{\n\treturn \"\";\n}\n<%org.eclipse.emf.common.util.URI%> _uRI = resource.getURI();\n<%java.lang.String%> _lastSegment = _uRI.lastSegment();\n<%java.lang.String%> _plus = (_lastSegment + \" - \");\n<%java.lang.String%> _name = this.getName();\nreturn (_plus + _name);'"
	 * @generated
	 */
	String getResourceSimpleName();

} // LTypedPackage
