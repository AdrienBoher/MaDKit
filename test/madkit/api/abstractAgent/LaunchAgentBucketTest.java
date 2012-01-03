/*
 * Copyright 1997-2011 Fabien Michel, Olivier Gutknecht, Jacques Ferber
 * 
 * This file is part of MadKit.
 * 
 * MadKit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MadKit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with MadKit. If not, see <http://www.gnu.org/licenses/>.
 */
package madkit.api.abstractAgent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import madkit.agr.Organization;
import madkit.kernel.AbstractAgent;
import madkit.kernel.AbstractAgent.State;
import madkit.kernel.Madkit.LevelOption;
import madkit.kernel.JunitMadKit;
import madkit.testing.util.agent.SimulatedAgent;

import org.junit.Test;

/**
 * @author Fabien Michel
 * @since MadKit 5.0.0.14
 * @version 0.9
 * 
 */
@SuppressWarnings("serial")
public class LaunchAgentBucketTest extends JunitMadKit {
	
	static int size = 14;

	@Test
	public void returnSuccess() {
		for (int i = 0; i < 500; i++) {
			launchTest(new AbstractAgent() {
				protected void activate() {
					List<AbstractAgent> l = launchAgentBucket(SimulatedAgent.class.getName(), size);
					assertEquals(size, l.size());
					testAgents(l);
					assertEquals(size, getAgentsWithRole(COMMUNITY, GROUP, ROLE).size());
				}

			});
		}
	}

	private void testAgents(List<AbstractAgent> l) {
		for (AbstractAgent abstractAgent : l) {
			assertTrue(abstractAgent.isAlive());
			assertEquals(State.ACTIVATED,abstractAgent.getState());
			assertTrue(((SimulatedAgent) abstractAgent).goneThroughActivate());
		}
	}

	@Test
	public void returnSuccessOn0() {
		launchTest(new AbstractAgent() {
			protected void activate() {
				List<AbstractAgent> l = launchAgentBucket(SimulatedAgent.class.getName(), 0);
				assertEquals(0, l.size());
				assertEquals(null, getAgentsWithRole(COMMUNITY, GROUP, ROLE));
				testAgents(l);
			}
		});
	}

	@Test
	public void returnSuccessOn1() {
		launchTest(new AbstractAgent() {
			protected void activate() {
				List<AbstractAgent> l = launchAgentBucket(SimulatedAgent.class.getName(), 1);
				assertEquals(1, l.size());
				assertEquals(1, getAgentsWithRole(COMMUNITY, GROUP, ROLE).size());
				testAgents(l);
			}
		});
	}
	
	@Test
	public void returnSuccessWithName() {
		launchTest(new AbstractAgent() {
			protected void activate() {
				List<AbstractAgent> l = launchAgentBucketWithRoles(SimulatedAgent.class.getName(), size,Arrays.asList(COMMUNITY+";"+GROUP+";"+ROLE));
				assertEquals(size, l.size());
				assertEquals(size, getAgentsWithRole(COMMUNITY, GROUP, ROLE).size());
				//I am the manager
				assertEquals(null, getAgentsWithRole(COMMUNITY, GROUP, Organization.GROUP_MANAGER_ROLE));
				testAgents(l);
			}
		});
	}

	
}