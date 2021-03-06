/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.jmx.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.management.MBeanServer;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.management.MessageChannelMetrics;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.integration.monitor.MetricsFactory;
import org.springframework.integration.test.util.TestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Fisher
 * @author Oleg Zhurakousky
 * @author Gunnar Hillert
 * @author Gary Russell
 * @since 2.0
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class MBeanExporterParserTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void testMBeanExporterExists() throws InterruptedException {
		IntegrationMBeanExporter exporter = this.context.getBean(IntegrationMBeanExporter.class);
		MBeanServer server = this.context.getBean("mbs", MBeanServer.class);
		Properties properties = TestUtils.getPropertyValue(exporter, "objectNameStaticProperties", Properties.class);
		assertNotNull(properties);
		assertEquals(2, properties.size());
		assertTrue(properties.containsKey("foo"));
		assertTrue(properties.containsKey("bar"));
		assertEquals(server, exporter.getServer());
		assertSame(context.getBean("keyNamer"), TestUtils.getPropertyValue(exporter, "namingStrategy"));
		MessageChannelMetrics metrics = context.getBean("foo", MessageChannelMetrics.class);
		assertTrue(metrics.isCountsEnabled());
		assertFalse(metrics.isStatsEnabled());
		metrics = context.getBean("bar", MessageChannelMetrics.class);
		assertTrue(metrics.isCountsEnabled());
		assertFalse(metrics.isStatsEnabled());
		metrics = context.getBean("baz", MessageChannelMetrics.class);
		assertFalse(metrics.isCountsEnabled());
		assertFalse(metrics.isStatsEnabled());
		metrics = context.getBean("qux", MessageChannelMetrics.class);
		assertFalse(metrics.isCountsEnabled());
		assertFalse(metrics.isStatsEnabled());
		metrics = context.getBean("fiz", MessageChannelMetrics.class);
		assertTrue(metrics.isCountsEnabled());
		assertTrue(metrics.isStatsEnabled());
		metrics = context.getBean("buz", MessageChannelMetrics.class);
		assertTrue(metrics.isCountsEnabled());
		assertTrue(metrics.isStatsEnabled());
		MetricsFactory factory = context.getBean(MetricsFactory.class);
		assertSame(factory, TestUtils.getPropertyValue(exporter, "metricsFactory"));
		exporter.destroy();
	}

}
