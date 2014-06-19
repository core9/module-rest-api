package io.core9.plugin.test;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.testComplete;
import io.vertx.rxcore.java.eventbus.RxEventBus;
import io.vertx.rxcore.java.eventbus.RxMessage;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.vertx.testtools.TestVerticle;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.observables.BlockingObservable;

public class EventBusIntegrationTest extends TestVerticle {

	@Test
	public void testBlocking() {
		
		
		BlockingObservable<String> obs = BlockingObservable.from(Observable.from("one", "two", "three"));

		assertEquals("three", obs.last());
		System.out.print(obs.last());
		testComplete();
	}



	@Test
	public void testConcatResults() throws InterruptedException {
		final RxEventBus rxEventBus = new RxEventBus(vertx.eventBus());

		rxEventBus.<String> registerHandler("foo").subscribe(new Action1<RxMessage<String>>() {
			@Override
			public void call(RxMessage<String> msg) {
				msg.reply("pong" + msg.body());
			}
		});

		List<Observable<RxMessage<String>>> args = new ArrayList<Observable<RxMessage<String>>>();
		String[] myStringArray = { "A", "B", "C" };

		for (int i : range(0, 2)) {
			String charr = myStringArray[i];
			Observable<RxMessage<String>> obs1 = rxEventBus.send("foo", charr);
			args.add(obs1);
		}

		Observable<RxMessage<String>> merged = Observable.merge(args);

		Observable<String> result = merged.reduce("", new Func2<String, RxMessage<String>, String>() {
			@Override
			public String call(String accum, RxMessage<String> reply) {
				// System.out.println(accum);
				return accum + reply.body();
			}
		});

		RxJavaUtils utils = new RxJavaUtils();

		utils.assertSequenceThenComplete(result.takeLast(1), "pongApongBpongC");

		/*
		 * Action1<String> observer = new Action1<String>() {
		 * 
		 * @Override public void call(String t1) { System.out.println(t1);
		 * globalResult = t1;
		 * 
		 * testComplete(); } }; // result.subscribe(observer);
		 * 
		 * System.out.println(""); result.subscribe(observer);
		 */

	}

	public int[] range(int start, int length) {
		int[] range = new int[length - start + 1];
		for (int i = start; i <= length; i++) {
			range[i - start] = i;
		}
		return range;
	}

}
