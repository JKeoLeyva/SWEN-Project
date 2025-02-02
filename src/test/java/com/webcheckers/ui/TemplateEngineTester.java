package com.webcheckers.ui;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import spark.ModelAndView;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Helper class to extract data from Spark's {@link ModelAndView} objects
 * that are passed to the {@code TemplateEngine.render} method.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
class TemplateEngineTester {

  /** Holds the View-Model map from the Spark ModelAndView object. */
  private Object model;
  /** Holds the View name from the Spark ModelAndView object. */
  private String viewName;

  /**
   * Make a Mockito {@link Answer} object to capture the {@link ModelAndView}
   * parameter to the template engine render method.
   *
   * @return  a Mockito {@link Answer} object
   */
  Answer<Object> makeAnswer() {
    return new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocation) {
        final ModelAndView modelAndView = invocation.getArgument(0);
        TemplateEngineTester.this.model = modelAndView.getModel();
        TemplateEngineTester.this.viewName = modelAndView.getViewName();
        // the return value is not of interest
        return null;
      }
    };
  }

  /**
   * Assert that the View-Model object exists.
   */
  void assertViewModelExists() {
    assertNotNull(model, "the View-Model exists");
  }

  /**
   * Assert that the View-Model object exists.
   */
  void assertViewModelIsaMap() {
    assertTrue(model instanceof Map, "the View-Model is a Map");
  }

  /**
   * Assert that the View-Model attributes matches the expected value.
   */
  void assertViewModelAttribute(final String attrName, final Object expectedValue) {
    @SuppressWarnings("unchecked")
    final Map<String, Object> vm = (Map<String, Object>) model;
    assertEquals(expectedValue, vm.get(attrName));
  }

  /**
   * Assert that the View-Model attribute is absent.
   */
  void assertViewModelAttributeIsAbsent(final String attrName) {
    @SuppressWarnings("unchecked")
    final Map<String, Object> vm = (Map<String, Object>) model;
    assertFalse(vm.containsKey(attrName));
  }

  /**
   * Assert that the View-Model attribute exists.
   */
  void assertViewModelAttributeIsPresent(final String attrName) {
    @SuppressWarnings("unchecked")
    final Map<String, Object> vm = (Map<String, Object>) model;
    assertTrue(vm.containsKey(attrName));
  }

  /**
   * Assert that the View name exists and matches the expected value.
   */
  void assertViewName(final String expectedName) {
    assertAll("View assertions",
        () -> { assertNotNull(viewName, "the View name exists"); },
        () -> { assertEquals(expectedName, viewName, "the View name matches"); }
        );
  }
}
