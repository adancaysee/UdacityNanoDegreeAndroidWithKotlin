package com.udacity.devbyteviewer

/**
 * Caching
 * 1.Network Caching
 * 2.Caching with SQL Database
 * 3.Caching with file system
 *
 * 1. Network Caching
 *   -- Retrofit
 *   -- Store and load network request on disk
 *
 * 2.Caching with SQL Database
 *   -- Room
 *   -- Handle Complexity (SharedPreferences are only key/value , for simple data)
 *   -- Keep locally on the device file system (Instead of hosting the database on a server)
 *   -- Our data may be combination of several network requests
 *
 * 3. Files
 *   -- Store anything you want
 *   -- Managing a file is complex
 *
 *   *** Offline cache's keys
 *       1. Always show data from the database
 *       2. Server saves value every time --> database is up to date
 */