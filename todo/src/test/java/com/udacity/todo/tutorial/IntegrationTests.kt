package com.udacity.todo.tutorial

/**
 * NOTE :
 * For example in above hierarchy
 * FOR REAL : Application -> Activity -> Fragment -> ViewModel -> Repository -> DataSources
 *   1. Application -> Activity = Empty Activity (Fragment Scenario)
 *   2. Repository -> DataSources = Fake Repository(Fake Test Double)
 * FOR TESTING : Fragment -> ViewModel
 *    We can make integration test for Fragment and ViewModel pair
 *    For this way as mush as possible we can avoid unrelated code from anything above or below in the architecture diagram
 *
 * FragmentScenario API
 * This Api give us fragments and activities for testing --> I can control starting condition and lifecycle state of
 * Fragment tests are instrumentation test
 * If I want to test something visual , I prefer to make that a instrumentation test
 *
 *
 */