package com.udacity.todo.tutorial

/**
 * TESTING TYPE
 * 1. Manuel Testing -> Human
 * 2. Automated Test -> Machine and code
 *
 * READABLE TESTING
 * 1. Naming -> subjectUnderTest_actionOrInput_resultState
 * 2. GIVEN/WHEN/THEN --> add comment to test function
 * 3. Assertion Framework --> Truth,Hamcrest
 *
 * AUTOMATED TEST
 * 1.UNIT TEST ( %70 )
 *   Scope of a single action or class
 *   Helps pinpoint failure (You know exactly when in your code the issue is)
 *   Should run fast,usually local
 *   Low fidelity (the app is more than an execution of one method or class)
 *
 * 2.INTEGRATION TEST ( %20 )
 *  Several classes or a single feature
 *  Ensure class can work together
 *  Larger scope than unit test
 *  Can be local or instrumented test
 *
 * 3.END-TO-END TEST ( %10 )
 *  Large portions of the app
 *  Combination of features working together
 *  High fidelity
 *  Test that the app works as a whole
 *  Simulate real usage,almost always instrumented test
 *
 * ** OTHER NOTES
 * When to writing unit test for a class , the goal is to only test the code in that class
 */