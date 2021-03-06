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
package org.lunifera.dsl.entity.xtext.extensions

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.util.TypeReferences
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.lunifera.dsl.semantic.common.types.LClass
import org.lunifera.dsl.semantic.common.types.LDataType
import org.lunifera.dsl.semantic.common.types.LFeature
import org.lunifera.dsl.semantic.entity.LBean
import org.lunifera.dsl.semantic.entity.LBeanAttribute
import org.lunifera.dsl.semantic.entity.LBeanReference
import org.lunifera.dsl.semantic.entity.LDiscriminatorType
import org.lunifera.dsl.semantic.entity.LEntity
import org.lunifera.dsl.semantic.entity.LEntityAttribute
import org.lunifera.dsl.semantic.entity.LEntityInheritanceStrategy
import org.lunifera.dsl.semantic.entity.LEntityReference
import org.lunifera.dsl.semantic.entity.LOperation
import org.lunifera.dsl.semantic.entity.LTablePerClassStrategy
import org.lunifera.dsl.semantic.entity.LTablePerSubclassStrategy
import org.lunifera.dsl.semantic.entity.LunEntityFactory
import org.lunifera.dsl.semantic.common.types.LEnum

class ModelExtensions extends org.lunifera.dsl.common.xtext.extensions.ModelExtensions {

	@Inject extension IQualifiedNameProvider
	@Inject extension JvmTypesBuilder
	@Inject extension NamingExtensions

	@Inject TypeReferences references;

	def dispatch JvmTypeReference toTypeReference(LEntityReference prop) {
		return prop.typeJvm?.cloneWithProxies
	}
	
	def dispatch JvmTypeReference toTypeReference(LEntityAttribute prop) {
		val lType = prop.type
		if(lType instanceof LEntity || lType instanceof LBean || lType instanceof LEnum){
			return prop.typeJvm?.cloneWithProxies
		}
		return prop.type?.toTypeReference
	}

	def dispatch JvmTypeReference toTypeReference(LBeanReference prop) {
		return prop.typeJvm?.cloneWithProxies
	}

	def dispatch JvmTypeReference toTypeReference(LOperation prop) {
		prop.type.cloneWithProxies
	}

	override JvmTypeReference toSyntheticTypeReference(LDataType type) {
		switch (type.syntheticSelector) {
			case Constants::DT_INTERNAL_IS_CURRENT_VERSION:
				references.findDeclaredType(Boolean::TYPE, type).newTypeRef()
			case Constants::DT_INTERNAL_OBJECT_VERSION:
				references.findDeclaredType(Integer::TYPE, type).newTypeRef()
			case Constants::DT_INTERNAL_OBJECT_ID:
				if (type.syntheticType != null) {
					for (att : (type.syntheticType as LEntity).allAttributes) {
						if (att.id || att.uuid) {
							return att.toTypeReference
						}
					}
				}
		}
	}

	def dispatch boolean isCascading(LEntityReference prop) {
		prop.cascading || if(prop.opposite != null) prop.opposite.cascading else false
	}

	def dispatch boolean isCascading(LBeanReference prop) {
		prop.cascading || if(prop.opposite != null) prop.opposite.cascading else false
	}

	def dispatch boolean isCascading(LOperation prop) {
		return false
	}

	def dispatch boolean isUUID(LFeature prop) {
		false
	}

	def dispatch boolean isUUID(LEntityAttribute prop) {
		prop.uuid
	}
 
	def dispatch getResolvedOpposite(LEntityReference prop) {

		// For a toMany that has already an opposite, return it.
		// Otherwise search in the referenced type for the property with the owner type.
		if (prop.getOpposite() != null) {
			return prop.getOpposite()
		} else if (prop.type instanceof LEntity) {
			val LEntity ref = prop.type as LEntity
			ref.references.findFirst[it.opposite == prop]
		}
	}

	def dispatch getResolvedOpposite(LBeanReference prop) {

		// For a toMany that has already an opposite, return it.
		// Otherwise search in the referenced type for the property with the owner type.
		if (prop.getOpposite() != null) {
			return prop.getOpposite()
		} else if (prop.type instanceof LBean) {
			val LBean ref = prop.type as LBean
			ref.references.findFirst[it.opposite == prop]
		}
	}

	/**
   	 * Returns true, if toCheck can be cast to superType
   	 */
	def dispatch boolean isCastable(LEntity toCheck, LEntity superType) {
		val String toCheckFqn = toCheck.fullyQualifiedName.toString
		val String superTypeFqn = superType.fullyQualifiedName.toString

		if (toCheckFqn.equals(superTypeFqn)) {
			return true
		} else {
			val LClass toCheckSuperType = toCheck.getSuperType
			if (toCheckSuperType != null) {
				return toCheckSuperType.isCastable(superType)
			} else {
				return false;
			}
		}
	}

	/**
   	 * Returns true, if toCheck can be cast to superType
   	 */
	def dispatch boolean isCastable(LBean toCheck, LBean superType) {
		val String toCheckFqn = toCheck.fullyQualifiedName.toString
		val String superTypeFqn = superType.fullyQualifiedName.toString

		if (toCheckFqn.equals(superTypeFqn)) {
			return true
		} else {
			val LClass toCheckSuperType = toCheck.getSuperType
			if (toCheckSuperType != null) {
				return toCheckSuperType.isCastable(superType)
			} else {
				return false;
			}
		}
	}

	/**
	 * The binary <code>+</code> operator that concatenates two strings.
	 * 
	 * @param a
	 *            a string.
	 * @param b
	 *            another string.
	 * @return <code>a + b</code>
	 */
	def static String operator_plus(String a, String b) {
		return a + b;
	}

	def dispatch String typeName(LEntityAttribute prop) {
		prop.type.name
	}

	def dispatch String typeName(LEntityReference prop) {
		prop.type.name
	}

	def dispatch String typeName(LBeanAttribute prop) {
		prop.type.name
	}

	def dispatch String typeName(LBeanReference prop) {
		prop.type.name
	}

	def dispatch type(LEntityAttribute prop) {
		prop.type
	}

	def dispatch type(LEntityReference prop) {
		prop.type
	}

	def dispatch type(LBeanAttribute prop) {
		prop.type
	}

	def dispatch type(LBeanReference prop) {
		prop.type
	}

	def dispatch opposite(LEntityAttribute prop) {
		null
	}

	def dispatch opposite(LEntityReference prop) {
		prop.opposite
	}

	def dispatch opposite(LBeanAttribute prop) {
		null
	}

	def dispatch opposite(LBeanReference prop) {
		prop.opposite
	}

	def List<LEntityInheritanceStrategy> collectAllInheritanceStrategies(LEntity entity) {
		return entity.collectAllInheritanceStrategies(newArrayList())
	}

	def protected List<LEntityInheritanceStrategy> collectAllInheritanceStrategies(LEntity entity,
		List<LEntityInheritanceStrategy> result) {
		if (entity == null) {
			return result
		}
		result += entity.toInheritanceStrategy

		return entity.superType.collectAllInheritanceStrategies(result)
	}

	def toInheritanceStrategy(LEntity entity) {
		var LEntityInheritanceStrategy strategy = entity.inheritanceStrategy
		if (strategy != null) {
			strategy = EcoreUtil::copy(strategy)
		}

		if (strategy == null) {
			strategy = entity.findStrategyFromSuper
			strategy?.resetInheritancePropsFromSuper(entity)
		}

		if (strategy == null) {
			strategy = LunEntityFactory::eINSTANCE.createLTablePerSubclassStrategy
		}

		strategy.fillInheritanceDefaults(entity)

		return strategy
	}

	def dispatch void fillInheritanceDefaults(LTablePerClassStrategy strategy, LEntity entity) {
		if (strategy.discriminatorColumn == null) {
			strategy.discriminatorColumn = "DISC"
		}

		if (strategy.discriminatorType == LDiscriminatorType::INHERIT) {
			strategy.discriminatorType = LDiscriminatorType::STRING
		}

		if (strategy.discriminatorValue == null) {
			strategy.discriminatorValue = entity.toTableName
		}
	}

	def dispatch void fillInheritanceDefaults(LTablePerSubclassStrategy strategy, LEntity entity) {
		if (strategy.discriminatorColumn == null) {
			strategy.discriminatorColumn = "DISC"
		}

		if (strategy.discriminatorType == LDiscriminatorType::INHERIT) {
			strategy.discriminatorType = LDiscriminatorType::STRING
		}

		if (strategy.discriminatorValue == null) {
			strategy.discriminatorValue = entity.toTableName
		}
	}

	def dispatch void resetInheritancePropsFromSuper(LTablePerClassStrategy strategy, LEntity entity) {
		strategy.discriminatorValue = null
	}

	def dispatch void resetInheritancePropsFromSuper(LTablePerSubclassStrategy strategy, LEntity entity) {
		strategy.discriminatorValue = null
	}

	def dispatch String toDiscriminatorValue(LTablePerClassStrategy strategy) {
		strategy.discriminatorValue
	}

	def dispatch String toDiscriminatorValue(LTablePerSubclassStrategy strategy) {
		strategy.discriminatorValue
	}

	def LEntityInheritanceStrategy findStrategyFromSuper(LEntity entity) {
		if (entity.inheritanceStrategy != null) {
			return EcoreUtil::copy(entity.inheritanceStrategy)
		}
		if (entity != entity.superType && entity.superType != null) {
			return entity.superType.findStrategyFromSuper
		}
		return null
	}

	def boolean isStrategyFromSuperPresent(LEntity entity) {
		return entity.findStrategyFromSuper != null
	}

	def boolean isStrategyPerSubclass(LEntity entity) {
		return entity.toInheritanceStrategy instanceof LTablePerSubclassStrategy
	}

	def boolean checkIsMappedSuperclass(LEntity entity) {
		return if(entity == null) false else entity.mappedSuperclass
	}
}
