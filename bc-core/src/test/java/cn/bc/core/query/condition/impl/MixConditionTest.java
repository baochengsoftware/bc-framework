/**
 * 
 */
package cn.bc.core.query.condition.impl;

import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.OrCondition;
import cn.bc.core.query.condition.impl.OrderCondition;


/**
 * 
 * @author dragon
 * 
 */
public class MixConditionTest {
	@Test
	public void and1_orderBy() {
		AndCondition and = new AndCondition();
		and.add(new EqualsCondition("key1", "value1")).add(
				new OrderCondition("key", Direction.Asc));
		Assert.assertEquals("key1 = ? order by key asc", and
				.getExpression());
		Assert.assertNotNull(and.getValues());
		Assert.assertTrue(and.getValues().size() == 1);
		Assert.assertEquals("value1", and.getValues().get(0));
	}
	
	@Test
	public void and2_orderBy() {
		AndCondition and = new AndCondition();
		and.add(new EqualsCondition("key1", "value1")).add(
				new EqualsCondition("key2", "value2")).add(
				new OrderCondition("key", Direction.Asc));
		Assert.assertEquals("key1 = ? and key2 = ? order by key asc", and
				.getExpression());
		Assert.assertNotNull(and.getValues());
		Assert.assertTrue(and.getValues().size() == 2);
		Assert.assertEquals("value1", and.getValues().get(0));
		Assert.assertEquals("value2", and.getValues().get(1));
	}

	@Test
	public void or_orderBy() {
		OrCondition or = new OrCondition();
		or.add(new EqualsCondition("key1", "value1")).add(
				new EqualsCondition("key2", "value2")).add(
				new OrderCondition("key", Direction.Asc));
		Assert.assertEquals("key1 = ? or key2 = ? order by key asc", or
				.getExpression());
		Assert.assertNotNull(or.getValues());
		Assert.assertTrue(or.getValues().size() == 2);
		Assert.assertEquals("value1", or.getValues().get(0));
		Assert.assertEquals("value2", or.getValues().get(1));
	}

	//TODO @Test
	public void and_or_orderBy() {
		System.out.println(Pattern.compile("order\\s*by\\s",
				Pattern.CASE_INSENSITIVE).matcher("order by aa bb").replaceAll(
				""));

		AndCondition and = new AndCondition();
		and.add(new EqualsCondition("key1", "value1")).add(
				new EqualsCondition("key2", "value2")).add(
				new OrderCondition("order1", Direction.Asc));

		OrCondition or = new OrCondition();
		or.add(new EqualsCondition("key3", "value3")).add(
				new EqualsCondition("key4", "value4")).add(
				new OrderCondition("order2", Direction.Desc));

		and.add(or);
		System.out.println(and.getExpression());
		Assert
				.assertEquals(
						"key1 = ? and key2 = ? and key3 = ? or key4 = ? order by order1 asc, order2 desc",
						and.getExpression());
		Assert.assertNotNull(or.getValues());
		Assert.assertTrue(or.getValues().size() == 4);
		Assert.assertEquals("value1", or.getValues().get(0));
		Assert.assertEquals("value2", or.getValues().get(1));
		Assert.assertEquals("value3", or.getValues().get(2));
		Assert.assertEquals("value4", or.getValues().get(3));
	}
}
