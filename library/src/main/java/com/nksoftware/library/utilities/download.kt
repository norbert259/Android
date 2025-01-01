/*
 * Copyright (c) Norbert Kraft 2025. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File: download.kt
 * Last modified: 01/01/2025, 14:01
 *
 */

package com.nksoftware.library.utilities

import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels


fun downloadFile(url: URL, outputFileName: String) {
   url.openStream().use {
      Channels.newChannel(it).use { rbc ->
         FileOutputStream(outputFileName, false).use { fos ->
            fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
         }
      }
   }
}
