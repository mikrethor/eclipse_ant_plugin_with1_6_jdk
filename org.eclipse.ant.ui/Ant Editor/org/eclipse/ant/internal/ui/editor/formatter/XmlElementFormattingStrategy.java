/*******************************************************************************
 * Copyright (c) 2004, 2013 John-Mason P. Shackelford and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John-Mason P. Shackelford - initial API and implementation
 * 	   IBM Corporation - bug 52076, bug 84342
 *******************************************************************************/

package org.eclipse.ant.internal.ui.editor.formatter;

import java.util.LinkedList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;

public class XmlElementFormattingStrategy extends ContextBasedFormattingStrategy {

	/** access to the preferences store * */
	private final FormattingPreferences prefs;

	/** Documents to be formatted by this strategy */
	private final LinkedList<IDocument> fDocuments = new LinkedList<IDocument>();
	/** Partitions to be formatted by this strategy */
	private final LinkedList<TypedPosition> fPartitions = new LinkedList<TypedPosition>();

	public XmlElementFormattingStrategy() {
		this.prefs = new FormattingPreferences();
	}

	public XmlElementFormattingStrategy(FormattingPreferences prefs) {
		Assert.isNotNull(prefs);
		this.prefs = prefs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategyExtension#format()
	 */
	@Override
	public void format() {

		super.format();

		final IDocument document = fDocuments.removeFirst();
		final TypedPosition partition = fPartitions.removeFirst();

		if (document == null || partition == null) {
			return;
		}

		try {
			String formatted = formatElement(document, partition);
			String partitionText = document.get(partition.getOffset(), partition.getLength());

			if (formatted != null && !formatted.equals(partitionText)) {
				document.replace(partition.getOffset(), partition.getLength(), formatted);
			}

		}
		catch (BadLocationException e) {
			// do nothing
		}
	}

	private String formatElement(IDocument document, TypedPosition partition) throws BadLocationException {

		String partitionText = document.get(partition.getOffset(), partition.getLength());

		IRegion line = document.getLineInformationOfOffset(partition.getOffset());

		int indentLength = partition.getOffset() - line.getOffset();
		String lineDelimiter = document.getLineDelimiter(document.getLineOfOffset(line.getOffset()));
		if (lineDelimiter == null) {
			lineDelimiter = TextUtilities.getDefaultLineDelimiter(document);
		}
		return XmlTagFormatter.format(partitionText, prefs, document.get(line.getOffset(), indentLength), lineDelimiter);

	}

	/*
	 * @see org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy#formatterStarts(org.eclipse.jface.text.formatter.IFormattingContext)
	 */
	@Override
	public void formatterStarts(final IFormattingContext context) {
		super.formatterStarts(context);

		fPartitions.addLast((TypedPosition) context.getProperty(FormattingContextProperties.CONTEXT_PARTITION));
		fDocuments.addLast((IDocument) context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM));
	}

	/*
	 * @see org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy#formatterStops()
	 */
	@Override
	public void formatterStops() {
		super.formatterStops();

		fPartitions.clear();
		fDocuments.clear();
	}
}