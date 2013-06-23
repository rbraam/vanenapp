/* Copyright 2012 Matthijs Laan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.stripesstuff.stripersist;

import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import org.w3c.dom.Node;
import javax.servlet.ServletContext;

/**
 * <p>
 * Initialize all persistence units by default.
 * </p>
 * 
 * @author Matthijs Laan
 * 
 */
public class DefaultInitializeSettings implements InitializeSettings {
    private SortedMap<String,Node> persistenceUnits;

    @Override
    public void init(SortedMap<String,Node> persistenceUnits, URL xml, ServletContext context) {
        this.persistenceUnits = persistenceUnits;
    }

	/**
	 * @return all persistence unit names
	 */
    @Override
    public List<String> getPersistenceUnitsToCreate() {
        return new ArrayList(persistenceUnits.keySet());
    }
}
