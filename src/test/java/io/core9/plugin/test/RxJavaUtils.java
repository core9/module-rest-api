package io.core9.plugin.test;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.assertTrue;
import static org.vertx.testtools.VertxAssert.fail;
import static org.vertx.testtools.VertxAssert.testComplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;


public class RxJavaUtils {
  
	
	private String result;

	public String getResult(){
		
		return result;
	}

  /** Assert a sequence then complete test */
  @SuppressWarnings("unchecked")
public  <T> void assertSequenceThenComplete(Observable<T> in, final T... exp) {
    assertSequenceThen(in, new Action0() {
      @Override public void call() {
        testComplete();
      }
    }, exp);
  }
  
  /** Assert a sequence then call an Action0 when complete */
  @SuppressWarnings("unchecked")
public  <T> void assertSequenceThen(Observable<T> in, final Action0 thenAction, final T... exp) {
    @SuppressWarnings("rawtypes")
	final List<T> expList=new ArrayList(Arrays.asList(exp));
    
    in.subscribe(
      new Action1<T>() {
        public void call(T value) {
        	
        	result = (String) value;
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
          System.out.println(result);
          thenAction.call();
        }
      });
  }

}
