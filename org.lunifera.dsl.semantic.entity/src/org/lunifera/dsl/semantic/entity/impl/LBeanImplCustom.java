/**
 * Copyright (c) 2011 - 2015, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *         Florian Pirchner - Initial implementation
 */
package org.lunifera.dsl.semantic.entity.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.lunifera.dsl.semantic.common.types.LAnnotationDef;
import org.lunifera.dsl.semantic.common.types.LAnnotationTarget;
import org.lunifera.dsl.semantic.entity.LunEntityPackage;
import org.lunifera.dsl.semantic.entity.LBean;

public class LBeanImplCustom extends LBeanImpl {

	/**
	 * Overrides super type to ensure bidirectional proxy resolving
	 */
	@Override
	public LBean getSuperType() {
		if (superType != null && superType.eIsProxy()) {
			InternalEObject oldSuperType = (InternalEObject) superType;
			LBean oldSuperEntity = (LBean) oldSuperType;
			superType = (LBean) eResolveProxy(oldSuperType);
			if (superType != oldSuperType) {

				// ATENTION: inverse add must be called since bidirectional
				// references uses proxy resolution for lazy linking. And the
				// sub_types added to proxy must be added to new superType
				for (LBean subType : oldSuperEntity.getSubTypes()) {
					((InternalEObject) superType).eInverseAdd(
							(InternalEObject) subType,
							LunEntityPackage.LBEAN__SUB_TYPES, LBean.class, null);
				}

				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LunEntityPackage.LBEAN__SUPER_TYPE, oldSuperType,
							superType));
			}
		}
		return superType;
	}
	
	@Override
	public EList<LAnnotationDef> getAnnotations() {
		LAnnotationTarget info = getAnnotationInfo();
		if(info != null){
			return info.getAnnotations();
		}
		return new BasicEList<LAnnotationDef>();
	}
}
