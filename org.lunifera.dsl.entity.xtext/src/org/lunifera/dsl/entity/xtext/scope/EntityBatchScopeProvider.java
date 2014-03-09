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
package org.lunifera.dsl.entity.xtext.scope;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.lunifera.dsl.common.xtext.scope.CommonBatchScopeProvider;
import org.lunifera.dsl.semantic.common.types.LunTypesPackage;
import org.lunifera.dsl.semantic.entity.LBeanReference;
import org.lunifera.dsl.semantic.entity.LEntityAttribute;
import org.lunifera.dsl.semantic.entity.LEntityReference;
import org.lunifera.dsl.semantic.entity.LunEntityPackage;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
@SuppressWarnings("restriction")
public class EntityBatchScopeProvider extends CommonBatchScopeProvider {
	@Override
	public IScope getScope(final EObject context, EReference reference) {
		if (reference == LunEntityPackage.Literals.LENTITY_REFERENCE__OPPOSITE) {
			return new EntityRefOppositeScope((LEntityReference) context);
		} else if (reference == LunEntityPackage.Literals.LBEAN_REFERENCE__OPPOSITE) {
			return new BeanRefOppositeScope((LBeanReference) context);
		} else if (reference == LunTypesPackage.Literals.LATTRIBUTE__TYPE) {
			return new DatatypesScope(super.getScope(context, reference),
					(LEntityAttribute) context, reference);
		}
		return super.getScope(context, reference);
	}

}