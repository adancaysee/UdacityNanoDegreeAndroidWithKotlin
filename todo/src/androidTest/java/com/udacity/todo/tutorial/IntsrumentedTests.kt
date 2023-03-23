package com.udacity.todo.tutorial

/**
 * Espresso --> Ui testing library (Interact with view and check their state)
 * This parts :
 *  1. Static Espresso method --> Starts espresso statement.Basically says you're going to do something with a view (ex:onView())
 *  2. ViewMatcher --> Finds view in the ui and about it's view states (ex:withId())
 *  3. ViewAction --> Something can be done to a view (ex:click() --> this is used with perform())
 *  4. ViewAssertion --> Asserting something about the view (ex:matches() --> this is used with check())
 *  NOTE : You must turn off animation on testing device. If animation on espresso test can be flaky
 *
 *    onView(withId(R.id.task_detail_complete_checkbox))
 *            perform(click())
 *            check(matches(isChecked()))
 *
 *     onView(withId()) --> find view and start statement --> The view
 *     perform(click()) --> perform click action on the view
 *     check(matches(isChecked())) --> check assertion , matches(isChecked()) --> is the view's state matches checked
 */