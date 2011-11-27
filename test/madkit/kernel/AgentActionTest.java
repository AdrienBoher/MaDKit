package madkit.kernel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import madkit.action.AgentAction;
import madkit.kernel.Madkit.LevelOption;
import madkit.messages.CommandMessage;

import org.junit.Test;

public class AgentActionTest extends JunitMadKit{

	
	@Test
	public void LAUNCH_AGENT() {
		addMadkitArgs(LevelOption.kernelLogLevel.toString(),"ALL");
		launchTest(new AbstractAgent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void activate() {
				AbstractAgent a = new Agent();
				CommandMessage<AgentAction> m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a);
				proceedCommandMessage(m);
				assertTrue(a.isAlive());
				assertFalse(a.hasGUI());

				a = new Agent();
				m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a,true);
				proceedCommandMessage(m);
				assertTrue(a.isAlive());
				assertTrue(a.hasGUI());

				a = new Agent();
				m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a,1,true);
				proceedCommandMessage(m);
				assertTrue(a.hasGUI());

				a = new Agent();
				m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a,0);
				proceedCommandMessage(m);
				assertFalse(a.hasGUI());

			}
		});
	}

	@Test
	public void LAUNCH_AGENT_wrongType() {
		addMadkitArgs(LevelOption.kernelLogLevel.toString(),"ALL");
		launchTest(new AbstractAgent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void activate() {
				AbstractAgent help = new AbstractAgent();
				launchAgent(help);
				createDefaultCGR(help);
				createDefaultCGR(this);
				AbstractAgent a = new Agent();
				CommandMessage<AgentAction> m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a,new Object());
				sendMessage(COMMUNITY, GROUP, ROLE, m);
				proceedCommandMessage(m);
				assertFalse(a.hasGUI());

				a = new Agent();
				m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,new Object(),true);
				proceedCommandMessage(m);
				assertFalse(a.hasGUI());
			}
		});
	}

	
	
	@Test
	public void LAUNCH_AGENT_null() {
		addMadkitArgs(LevelOption.kernelLogLevel.toString(),"ALL");
		launchTest(new AbstractAgent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void activate() {
				AbstractAgent help = new AbstractAgent();
				launchAgent(help);
				createDefaultCGR(help);
				createDefaultCGR(this);
				AbstractAgent a = new Agent();
				CommandMessage<AgentAction> m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,a,null);
				sendMessage(COMMUNITY, GROUP, ROLE, m);
				proceedCommandMessage(m);
				assertFalse(a.hasGUI());

				a = new Agent();
				m = new CommandMessage<AgentAction>(AgentAction.LAUNCH_AGENT,null,true);
				proceedCommandMessage(m);
				assertFalse(a.hasGUI());
			}
		});
	}

	
//	@Test
//	public final void testToString() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testGetActionFor() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testAddAllGlobalActionsTo() {
//		fail("Not yet implemented"); // TODO
//	}

}