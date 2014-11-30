/**
 * Copyright (c) 2011 - 2014, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 		Florian Pirchner - Initial implementation
 */
package org.lunifera.dsl.xtext.lazyresolver.api;

import org.eclipse.emf.ecore.EObject;

/**
 * This kind of resource allows to load the semantic element without installing
 * derived state.
 */
public interface ISemanticLoadingResource {

	/**
	 * Returns the semantic element for the given fragment without installing
	 * the derived state.
	 * 
	 * @param fragment
	 * @return
	 */
	EObject getSemanticElement(String fragment);
	
	/**
	 * Returns the root semantic element for the given fragment without installing
	 * the derived state.
	 * 
	 * @param fragment
	 * @return
	 */
	EObject getSemanticElement();

}
