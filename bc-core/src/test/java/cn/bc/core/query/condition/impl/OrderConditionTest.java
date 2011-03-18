/**
 * 
 */
package cn.bc.core.query.condition.impl;

import junit.framework.Assert;

import org.junit.Test;

import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.OrderCondition;


/**
 * 
 * @author dragon
 * 
 */
public class OrderConditionTest {
	@Test
	public void test() {
		OrderCondition c = new OrderCondition("key",Direction.Asc);
		Assert.assertEquals("key asc", c.getExpression());
		Assert.assertNull(c.getValues());
		
		c = new OrderCondition("key",Direction.Desc);
		Assert.assertEquals("key desc", c.getExpression());
		Assert.assertNull(c.getValues());
	}
}
