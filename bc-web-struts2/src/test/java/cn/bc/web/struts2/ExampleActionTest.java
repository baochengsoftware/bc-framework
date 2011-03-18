package cn.bc.web.struts2;

import org.apache.struts2.StrutsTestCase;
import com.opensymphony.xwork2.ActionProxy;

public class ExampleActionTest extends StrutsTestCase {

	public void testErrorMessage() throws Exception {
		request.setParameter("accountBean", "");
		ActionProxy proxy = getActionProxy("/createaccount");
		ExampleAction accountAction = (ExampleAction) proxy.getAction();
		proxy.execute();

		assertTrue(
				"Problem There were no errors present in fieldErrors but there should have been one error present",
				accountAction.getFieldErrors().size() == 1);
		assertTrue(
				"Problem field account.userName not present in fieldErrors but it should have been",
				accountAction.getFieldErrors().containsKey("accountBean"));
	}

	public void testCorrect() throws Exception {
		request.setParameter("accountBean", "Bruce");
		ActionProxy proxy = getActionProxy("/createaccount");
		ExampleAction accountAction = (ExampleAction) proxy.getAction();
		String result = proxy.execute();

		assertTrue(
				"Problem There were errors present in fieldErrors but there should not have been any errors present",
				accountAction.getFieldErrors().size() == 0);
		assertEquals(
				"Result returned form executing the action was not success but it should have been.",
				"success", result);
	}
}
