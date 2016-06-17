/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ant.internal.ui.datatransfer;

import org.eclipse.ant.internal.ui.model.AntElementNode;
import org.eclipse.ant.internal.ui.model.AntModelLabelProvider;
import org.eclipse.jface.viewers.StyledString;

import com.ibm.icu.text.MessageFormat;

/**
 * Ant javac task label provider for a table
 */
public class JavacTableLabelProvider extends AntModelLabelProvider {

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof AntElementNode) {
			AntElementNode node = (AntElementNode) element;
			AntElementNode parent = (AntElementNode) node.getParentNode();
			if (parent != null) {
				return new StyledString(MessageFormat.format(DataTransferMessages.JavacTableLabelProvider_0, new Object[] { parent.getLabel() }));
			}
		}
		return super.getStyledText(element);
	}
}
