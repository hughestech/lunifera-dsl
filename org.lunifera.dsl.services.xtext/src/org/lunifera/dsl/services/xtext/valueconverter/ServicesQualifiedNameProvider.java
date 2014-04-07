/**
 * Copyright (c) 2011 - 2012, Florian Pirchner - lunifera.org
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Hans Georg Gl??ckler - Initial implementation
 * 		Florian Pirchner - Initial implementation
 */
package org.lunifera.dsl.services.xtext.valueconverter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.lunifera.dsl.common.xtext.extensions.ModelExtensions;
import org.lunifera.dsl.common.xtext.valueconverter.CommonQualifiedNameProvider;
import org.lunifera.dsl.semantic.dto.LDto;
import org.lunifera.dsl.semantic.service.LDTOService;
import org.lunifera.dsl.semantic.service.LSupportedDTO;
import org.lunifera.dsl.semantic.service.LSupportedFilters;

import com.google.inject.Inject;

public class ServicesQualifiedNameProvider extends CommonQualifiedNameProvider {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	@Inject
	ModelExtensions extensions;

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj == null) {
			return QualifiedName.create("");
		}

		if (obj instanceof LSupportedFilters) {
			LSupportedFilters filters = (LSupportedFilters) obj;
			LSupportedDTO dto = filters.getParent();
			QualifiedName name = getFullyQualifiedName(dto);
			return name.append("supportedFilter");
		} else if (obj instanceof LSupportedDTO) {
			LSupportedDTO dto = (LSupportedDTO) obj;
			LDTOService service = dto.getParent().getService();
			QualifiedName name = getFullyQualifiedName(service);
			name.append("dto");
			
			EcoreUtil.resolveAll(dto.getDto());
			name.append(getFullyQualifiedName(dto.getDto()));
			
			return name;
		}

		return super.getFullyQualifiedName(obj);
	}

}