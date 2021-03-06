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
package org.lunifera.dsl.entity.xtext.ui.quickfix;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;
import org.lunifera.dsl.entity.xtext.validation.EntityGrammarJavaValidator;

public class EntityGrammarQuickfixProvider extends DefaultQuickfixProvider {

	@SuppressWarnings("restriction")
	@Fix(EntityGrammarJavaValidator.CODE__CASCADE_DIRECTION_INVALID)
	public void capitalizeName(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Remove cascade", "Removes the cascade keyword",
				null, new IModification() {
					public void apply(IModificationContext context)
							throws BadLocationException {
						IXtextDocument xtextDocument = context
								.getXtextDocument();
						xtextDocument.replace(issue.getOffset(), "cascade ".length(),
								"");
					}
				});
	}

}
