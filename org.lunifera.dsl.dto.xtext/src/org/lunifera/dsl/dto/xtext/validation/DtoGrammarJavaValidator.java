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

/*
 * generated by Xtext
 */
package org.lunifera.dsl.dto.xtext.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.lunifera.dsl.dto.xtext.extensions.DtoModelExtensions;
import org.lunifera.dsl.semantic.common.helper.Bounds;
import org.lunifera.dsl.semantic.common.types.LDataType;
import org.lunifera.dsl.semantic.common.types.LFeature;
import org.lunifera.dsl.semantic.common.types.LPackage;
import org.lunifera.dsl.semantic.common.types.LType;
import org.lunifera.dsl.semantic.common.types.LTypedPackage;
import org.lunifera.dsl.semantic.common.types.LunTypesPackage;
import org.lunifera.dsl.semantic.dto.LDto;
import org.lunifera.dsl.semantic.dto.LDtoAttribute;
import org.lunifera.dsl.semantic.dto.LDtoFeature;
import org.lunifera.dsl.semantic.dto.LDtoModel;
import org.lunifera.dsl.semantic.dto.LDtoReference;
import org.lunifera.dsl.semantic.dto.LunDtoPackage;

import com.google.inject.Inject;

/**
 * Custom validation rules.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
public class DtoGrammarJavaValidator extends
		org.lunifera.dsl.dto.xtext.validation.AbstractDtoGrammarJavaValidator {

	private static final String CODE__MISSING_OPPOSITE_REFERENCE = "104";
	private static final String CODE__BIDIRECTIONAL_CASCADE_INVALID = "105";
	private static final String CODE__CASCADE_DIRECTION_INVALID = "106";
	private static final String CODE__OPPOSITE_WITHOUT_CASCADE = "107";
	private static final String CODE__UUID_WRONG_TYPE = "108";

	private static final String CODE__DUPLICATE_ID = "110";
	private static final String CODE__DUPLICATE_VERSION = "111";
	private static final String CODE__DUPLICATE_PROPERTY_NAME = "112";
	private static final String CODE__DUPLICATE_DOMAIN_KEY = "113";
	private static final String CODE__DUPLICATE_DOMAIN_DESCRIPTION = "114";
	private static final String CODE__DOMAIN_KEY__NO_MANY = "115";
	private static final String CODE__DOMAIN_DESCRIPTION__NO_MANY = "116";
	private static final String CODE__DOMAIN_KEY__TYPE = "117";
	private static final String CODE__DOMAIN_DESCRIPTION__TYPE = "118";

	@Inject
	private IQualifiedNameProvider qnp;
	@Inject
	private DtoModelExtensions extensions;

	@Check
	public void checkDatatype_asPrimitive(LDataType dt) {
		super.checkDatatype_asPrimitive(dt);
	}

	@Check
	public void checkMulti_HasOppositeReference(LDtoReference prop) {
		if (extensions.isToMany(prop) && prop.getOpposite() == null) {
			error("A 'to-many' association needs an opposite reference.",
					LunDtoPackage.Literals.LDTO_REFERENCE__OPPOSITE,
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
					CODE__MISSING_OPPOSITE_REFERENCE, (String[]) null);
		}
	}

	@Check
	public void checkOpposite_NotAlsoCascading(LDtoReference prop) {
		if (prop.getOpposite() != null) {
			if (prop.isCascading() && prop.getOpposite().isCascading()) {
				error("Only one opposite may be specified as cascade",
						LunTypesPackage.Literals.LREFERENCE__CASCADING,
						CODE__BIDIRECTIONAL_CASCADE_INVALID, (String[]) null);
			}

			if (extensions.isToMany(prop.getOpposite())) {
				if (prop.isCascading()) {
					error("Cascade must not affect the common parent in a many-to-one relation",
							prop,
							LunTypesPackage.Literals.LREFERENCE__CASCADING,
							CODE__CASCADE_DIRECTION_INVALID, new String[0]);
				}
			}
		}
	}

	@Check
	public void checkOpposite_OneIsCascading(LDtoReference prop) {
		Bounds propBound = extensions.getBounds(prop);

		if (prop.getOpposite() != null) {
			Bounds oppositeBound = extensions.getBounds(prop.getOpposite());

			if (propBound.isToMany() || oppositeBound.isToMany()) {
				// no check required!
				return;
			}
		}

		if (prop.getOpposite() != null) {
			if (!prop.isCascading() && !prop.getOpposite().isCascading()) {
				error("Opposite references may only defined for cascading relations.",
						prop, LunTypesPackage.Literals.LREFERENCE__CASCADING,
						CODE__OPPOSITE_WITHOUT_CASCADE, new String[0]);
			}
		}
	}

	@Check
	public void checkProperties_JavaKeyWord(LFeature lprop) {
		super.checkProperties_JavaKeyWord(lprop);
	}

	@Check
	public void checkDuplicatePackages_InFile(LDtoModel lmodel) {
		Set<String> names = new HashSet<String>();
		int counter = -1;
		for (LPackage pkg : lmodel.getPackages()) {
			counter++;
			String pkgName = qnp.getFullyQualifiedName(pkg).toString();
			if (names.contains(pkgName)) {
				error(String.format("Package %s must not be defined twice!",
						pkgName), LunDtoPackage.Literals.LDTO_MODEL__PACKAGES,
						counter, CODE__DUPLICATE_LPACKAGE_IN_FILE,
						(String[]) null);
			}
			names.add(pkgName);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkDuplicateTypeInProject(LType type) {
		if (type instanceof LDataType) {
			return;
		}
		super.checkDuplicateType_InProject(type);
	}

	@Check(CheckType.NORMAL)
	public void checkDuplicateDatatypeInPackage(LTypedPackage pkg) {
		super.checkDuplicateDatatypeInPackage(pkg);
	}

	@Check(CheckType.NORMAL)
	public void checkDuplicatePackage_InProject(LPackage lPackage) {
		super.checkDuplicatePackage_InProject(lPackage);
	}

	@Check
	public void checkManyToMany(LDtoReference prop) {
		DtoModelExtensions extension = new DtoModelExtensions();
		if (prop.getOpposite() != null && extension.isToMany(prop)
				&& extension.isToMany(prop.getOpposite())) {
			error(String.format("ManyToMany relations are not permitted!", qnp
					.getFullyQualifiedName(prop).toString()),
					LunDtoPackage.Literals.LDTO_REFERENCE__OPPOSITE,
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
					CODE__MANY_TO_MANY__NOT_SUPPORTED, (String[]) null);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkJPA_Features(LDtoAttribute prop) {
		if (prop.isUuid()) {
			boolean typeOK = false;
			if (prop.getType() instanceof LDataType) {
				LDataType type = (LDataType) prop.getType();
				String typename = type.getJvmTypeReference().getQualifiedName();
				if (typename.equals("java.lang.String")) {
					typeOK = true;
				}
			}
			if (!typeOK) {
				error("UUIDs must be of type String.",
						LunTypesPackage.Literals.LATTRIBUTE__UUID,
						CODE__UUID_WRONG_TYPE, new String[0]);
			}
		}
		if (prop.isDomainKey()) {
			if (extensions.isToMany(prop)) {
				error("DomainDescription is not valid for one to many relations.",
						LunTypesPackage.Literals.LATTRIBUTE__DOMAIN_KEY,
						CODE__DOMAIN_KEY__NO_MANY, new String[0]);

			}
			if (prop.getType() instanceof LDataType) {
				LDataType type = (LDataType) prop.getType();
				String typename = type.getJvmTypeReference().getQualifiedName();
				if (!typename.equals("java.lang.String")) {
					error("DomainDescription is not valid for one to many relations.",
							LunTypesPackage.Literals.LATTRIBUTE__DOMAIN_KEY,
							CODE__DOMAIN_KEY__TYPE, new String[0]);
				}
			}
		}

		if (prop.isDomainDescription()) {
			if (extensions.isToMany(prop)) {
				error("DomainKey is not valid for one to many relations.",
						LunTypesPackage.Literals.LATTRIBUTE__DOMAIN_DESCRIPTION,
						CODE__DOMAIN_DESCRIPTION__NO_MANY, new String[0]);

			}
			if (prop.getType() instanceof LDataType) {
				LDataType type = (LDataType) prop.getType();
				String typename = type.getJvmTypeReference().getQualifiedName();
				if (!typename.equals("java.lang.String")) {
					error("DomainDescription must be of type String.",
							LunTypesPackage.Literals.LATTRIBUTE__DOMAIN_KEY,
							CODE__DOMAIN_DESCRIPTION__TYPE, new String[0]);
				}
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkJPA_Features(LDto dto) {

		int idCounter = 0;
		int versionCounter = 0;
		int domainKeyCounter = 0;
		int domainDescriptionCounter = 0;
		Map<String, Integer> attNames = new HashMap<String, Integer>();
		for (LFeature feature : dto.getAllFeatures()) {
			if (feature instanceof LDtoAttribute) {
				LDtoAttribute att = (LDtoAttribute) feature;
				if (att.isId() || att.isUuid()) {
					idCounter++;
				}
				if (att.isVersion()) {
					versionCounter++;
				}
				if (att.isDomainKey()) {
					domainKeyCounter++;
				}
				if (att.isDomainDescription()) {
					domainDescriptionCounter++;
				}
			}

			if (!attNames.containsKey(feature.getName())) {
				attNames.put(feature.getName(), 1);
			} else {
				int value = attNames.get(feature.getName());
				attNames.put(feature.getName(), ++value);
			}
		}

		if (idCounter > 1) {
			int i = 0;
			for (LDtoFeature feature : dto.getFeatures()) {
				if (feature instanceof LDtoAttribute) {
					if (((LDtoAttribute) feature).isId()
							|| ((LDtoAttribute) feature).isUuid()) {
						error("A DTO must only have one ID property.",
								LunDtoPackage.Literals.LDTO__FEATURES, i,
								CODE__DUPLICATE_ID, new String[0]);
						break;
					}
				}

				i++;
			}
		}
		if (versionCounter > 1) {
			int i = 0;
			for (LDtoFeature feature : dto.getFeatures()) {
				if (feature instanceof LDtoAttribute) {
					if (((LDtoAttribute) feature).isVersion()) {
						error("A DTO must only have one Version property.",
								LunDtoPackage.Literals.LDTO__FEATURES, i,
								CODE__DUPLICATE_VERSION, new String[0]);
						break;
					}
				}
				i++;
			}
		}

		if (domainKeyCounter > 1) {
			int i = 0;
			for (LDtoFeature feature : dto.getFeatures()) {
				if (feature instanceof LDtoAttribute) {
					if (((LDtoAttribute) feature).isDomainKey()) {
						error("A DTO must only have one DomainKey property.",
								LunDtoPackage.Literals.LDTO__FEATURES, i,
								CODE__DUPLICATE_DOMAIN_KEY, new String[0]);
						break;
					}
				}
				i++;
			}
		}

		if (domainDescriptionCounter > 1) {
			int i = 0;
			for (LDtoFeature feature : dto.getFeatures()) {
				if (feature instanceof LDtoAttribute) {
					if (((LDtoAttribute) feature).isDomainDescription()) {
						error("A DTO must only have one DomainDescription property.",
								LunDtoPackage.Literals.LDTO__FEATURES, i,
								CODE__DUPLICATE_DOMAIN_DESCRIPTION,
								new String[0]);
						break;
					}
				}
				i++;
			}
		}

		for (Map.Entry<String, Integer> entry : attNames.entrySet()) {
			if (entry.getValue() > 1) {
				int i = 0;
				for (LDtoFeature feature : dto.getFeatures()) {
					if (feature.getName().equals(entry.getKey())) {
						error(String.format(
								"The property \"%s\" must only be defined once!",
								feature.getName()),
								LunDtoPackage.Literals.LDTO__FEATURES, i,
								CODE__DUPLICATE_PROPERTY_NAME, new String[0]);
						break;
					}
					i++;
				}
			}
		}
	}

}
