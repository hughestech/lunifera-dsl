/*
 * generated by Xtext
 */
package org.lunifera.dsl.datatype.xtext.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.lunifera.dsl.datatype.xtext.ui.internal.DatatypeGrammarActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class DatatypeGrammarExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return DatatypeGrammarActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return DatatypeGrammarActivator.getInstance().getInjector(DatatypeGrammarActivator.ORG_LUNIFERA_DSL_DATATYPE_XTEXT_DATATYPEGRAMMAR);
	}
	
}