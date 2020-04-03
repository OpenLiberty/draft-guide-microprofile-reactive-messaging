// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryManager {

    private Map<String, Properties> systems = Collections.synchronizedMap(new TreeMap<String, Properties>());

    public void addSystem(String hostId, Double cpuUsage) {
        if (!systems.containsKey(hostId)) {
            Properties p = new Properties();
            p.put("hostname", hostId);
            p.put("cpuUsage", cpuUsage);
            systems.put(hostId, p);
        }
    }
    
    public void addSystem(String hostId, Long memoryUsed, Long memoryMax) {
        if (!systems.containsKey(hostId)) {
            Properties p = new Properties();
            p.put("hostname", hostId);
            p.put("memoryUsed", memoryUsed);
            p.put("memoryMax", memoryMax);
            systems.put(hostId, p);
        }
    }

	public void addSystem(String hostId, String key, String value) {
        if (!systems.containsKey(hostId)) {
            Properties p = new Properties();
            p.put("hostname", hostId);
            p.put("key", value);
            systems.put(hostId, p);
        }
	}
	
    public void updateCpuStatus(String hostId, Double cpuUsage) {
        Optional<Properties> p = getSystem(hostId);
        if (p.isPresent()) {
            if (p.get().getProperty(hostId) == null && hostId != null)
                p.get().put("cpuUsage", cpuUsage);
        }
    }

    public void updateMemoryStatus(String hostId, Long memoryUsed, Long memoryMax) {
        Optional<Properties> p = getSystem(hostId);
        if (p.isPresent()) {
            if (p.get().getProperty(hostId) == null && hostId != null) {
                p.get().put("memoryUsed", memoryUsed);
                p.get().put("memoryMax", memoryMax);
            }
        }
    }
    
	public void updatePropertyMessage(String hostId, String key, String value) {
        Optional<Properties> p = getSystem(hostId);
        if (p.isPresent()) {
            if (p.get().getProperty(hostId) == null && hostId != null) {
                p.get().put(key, value);
            }
        }
	}
	
    public Optional<Properties> getSystem(String hostId) {
        Properties p = systems.get(hostId);
        return Optional.ofNullable(p);
    }

    public Map<String, Properties> getSystems() {
        return new TreeMap<>(systems);
    }

    public void resetSystems() {
        systems.clear();
    }

}