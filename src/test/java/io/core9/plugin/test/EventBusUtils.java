package io.core9.plugin.test;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.assertTrue;
import static org.vertx.testtools.VertxAssert.fail;
import static org.vertx.testtools.VertxAssert.testComplete;
import io.core9.plugin.rest.RestRequest;
import io.core9.plugin.rest.RestRequestImpl;
import io.core9.plugin.rest.RestRouter;
import io.vertx.rxcore.java.eventbus.RxEventBus;
import io.vertx.rxcore.java.eventbus.RxMessage;
//import io.vertx.rxcore.test.integration.java.RxAssert;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;

public class EventBusUtils {

	private Vertx vertx = VertxFactory.newVertx();
	private final RxEventBus rxEventBus = new RxEventBus(vertx.eventBus());
	private HashMap<String, Object> processedResult;
	private RestRouter restRouter;

	private static final EventBusUtils INSTANCE = new EventBusUtils();

	private EventBusUtils() {
	}

	public static EventBusUtils getInstance() {
		return INSTANCE;
	}

	public EventBusUtils setRestRouter(RestRouter restRouter) {

		this.restRouter = restRouter;
		return this;
	}

	public Map<String, Map<String, Object>> procesRequest(Map<String, List<Map<String, RestRequest>>> collection) {

		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

		List<Map<String, RestRequest>> parallelCollection = collection.get("parallel");

		if (parallelCollection.size() > 0) {
			result.put("parallel", procesParallel(parallelCollection));
		}

		/*
		 * make this also a parallel result and add to parallel
		 * if(collection.get("serialize").size() > 0){ }
		 */

		return result;
	}

	private String getRandomNumber() {

		Random r = new Random();

		String alphabet = "123xyz";

		char charr = alphabet.charAt(r.nextInt(alphabet.length()));
		return Character.toString(charr);
	}

	private Map<String, Object> procesParallel(List<Map<String, RestRequest>> parallelCollection) {

		rxEventBus.<String> registerHandler("parallel").subscribe(new Action1<RxMessage<String>>() {
			@Override
			public void call(RxMessage<String> msg) {

				// RestRequest request = new RestRequestImpl();
				// request.fromJsonString(msg.body());

				// @SuppressWarnings("unused")
				// Object response = restRouter.getResponse(request);
				// msg.reply("pong" + msg.body());
				msg.reply("pong" + msg.body());
			}
		});

		List<Observable<RxMessage<String>>> args = new ArrayList<Observable<RxMessage<String>>>();

/*		for (Map<String, RestRequest> jobs : parallelCollection) {

			for (Entry<String, RestRequest> job : jobs.entrySet()) {
				String jobRequest = job.getValue().toJson().toString();
				// Observable<RxMessage<String>> obs1 =
				// rxEventBus.send("parallel", jobRequest);
				Observable<RxMessage<String>> obs1 = rxEventBus.send("parallel", getRandomNumber());
				args.add(obs1);
			}

		}*/
		
		
		String[] myStringArray = {"A","B","C"};
		
		for(int i : range(0, 2)){
			String charr = myStringArray[i];
			Observable<RxMessage<String>> obs1 = rxEventBus.send("parallel", charr);
			args.add(obs1);
		}

		/*
		 * Observable<RxMessage<String>> obs1 = rxEventBus.send("parallel",
		 * "A"); Observable<RxMessage<String>> obs2 =
		 * rxEventBus.send("parallel", "B"); Observable<RxMessage<String>> obs3
		 * = rxEventBus.send("parallel", "C");
		 */

		Observable<RxMessage<String>> merged = Observable.merge(args);

		Observable<String> result = merged.reduce("", new Func2<String, RxMessage<String>, String>() {

			@Override
			public String call(String accum, RxMessage<String> reply) {

				// JSONObject mes = (JSONObject) JSONValue.parse(reply.body());

				// processedResult.put((String) mes.get("rxJavaVarName"), mes);
				System.out.println(accum);
				return accum + reply.body();
			}
		});

		assertSequenceThenComplete(result.takeLast(1), "pongApongBpongC");

		System.out.println(result.takeLast(1));

		return processedResult;

	}
	
	
	public int[] range(int start, int length) {
	    int[] range = new int[length - start + 1];
	    for (int i = start; i <= length; i++) {
	        range[i - start] = i;
	    }
	    return range;
	}
	
	  /** Assert a sequence then complete test */
	  @SuppressWarnings("unchecked")
	public static <T> void assertSequenceThenComplete(Observable<T> in, final T... exp) {
	    assertSequenceThen(in, new Action0() {
	      @Override public void call() {
	        testComplete();
	      }
	    }, exp);
	  }
	  
	  /** Assert a sequence then call an Action0 when complete */
	  @SuppressWarnings("unchecked")
	public static <T> void assertSequenceThen(Observable<T> in, final Action0 thenAction, final T... exp) {
	    @SuppressWarnings("rawtypes")
		final List<T> expList=new ArrayList(Arrays.asList(exp));
	    in.subscribe(
	      new Action1<T>() {
	        public void call(T value) {
	        	System.out.println("The value : " + value.toString());
	          assertEquals(expList.remove(0),value);
	        }
	      },
	      new Action1<Throwable>() {
	        public void call(Throwable t) {
	          fail("Error while mapping sequence (t="+t+")");
	        }
	      },
	      new Action0() {
	        public void call() {
	          assertTrue(expList.isEmpty());
	          System.out.println("sequence-complete");
	          thenAction.call();
	        }
	      });
	  }
	
}
