package com.udacity.eggtimer.tutor

/**
 * Foreground Permissions
 * ACCESS_FINE_LOCATION --> precise location
 * ACCESS_COARSE_LOCATION --> approximate location
 * On Api 31 and higher --> permission pop-up shows both of them
 *
 * Background Permission
 * For Api 29 and higher
 * Firstly you must get permissions for foreground permissions
 * On Api 29(Android 10) --> Showing permission dialog
 * On Api 30(Android 11) and higher --> If request a foreground location permission and the background
 *                                      location permission at the same time, the system ignores the request
 *                                  --> Showing permission screen
 */

/**
 * Request Permission Steps
 * 1. Add permission to manifest
 * 2. Register activity for request permission
 */