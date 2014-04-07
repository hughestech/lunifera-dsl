/*
 * generated by Xtext
 */
package org.lunifera.dsl.services.xtext.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.lunifera.dsl.services.xtext.ui.internal.ServicesGrammarActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class ServicesGrammarExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return ServicesGrammarActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return ServicesGrammarActivator.getInstance().getInjector(ServicesGrammarActivator.ORG_LUNIFERA_DSL_SERVICES_XTEXT_SERVICESGRAMMAR);
	}
	
}