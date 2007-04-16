/*******************************************************************************
 * Copyright (c) 2006 Brad Reynolds
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brad Reynolds - bug 158687
 *     Brad Reynolds - bug 164653
 ******************************************************************************/

package org.eclipse.core.tests.databinding.observable.value;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.jface.tests.databinding.EventTrackers.ValueChangeEventTracker;
import org.eclipse.swt.widgets.Display;

/**
 * @since 3.2
 */
public class WritableValueTest extends AbstractDefaultRealmTestCase {
	/**
	 * Asserts that ValueChange events are only fired when the value changes.
	 * 
	 * @throws Exception
	 */
	public void testValueChangeOnlyFiresOnChange() throws Exception {
		WritableValue writableValue = new WritableValue();
		ValueChangeEventTracker counter = new ValueChangeEventTracker();
		writableValue.addValueChangeListener(counter);

		assertEquals(0, counter.count);
		// set same
		writableValue.setValue(null);
		assertEquals(0, counter.count);

		// set different
		writableValue.setValue("value");
		assertEquals(1, counter.count);

		// set same
		writableValue.setValue("value");
		assertEquals(1, counter.count);

		// set different
		writableValue.setValue(null);
		assertEquals(2, counter.count);
	}

	public void testDoSetValue() throws Exception {
		WritableValue writableValue = new WritableValue(SWTObservables
				.getRealm(Display.getDefault()));
		Object value = new Object();
		writableValue.setValue(value);
		assertEquals(value, writableValue.getValue());
	}

	/**
	 * All constructors delegate to the 3 arg constructor.
	 * 
	 * @throws Exception
	 */
	public void testConstructor() throws Exception {
		WritableValue value = new WritableValue(SWTObservables.getRealm(Display
				.getDefault()));
		assertNull(value.getValue());
		assertNull(value.getValueType());
	}
	
	public void testWithValueType() throws Exception {
		Object elementType = String.class;
		WritableValue value = WritableValue.withValueType(elementType);
		assertNotNull(value);
		assertEquals(Realm.getDefault(), value.getRealm());
		assertEquals(elementType, value.getValueType());
	}
}