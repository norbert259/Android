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
 * File: OsmMapOptions.kt
 * Last modified: 01/01/2025, 14:01
 *
 */

package com.nksoftware.skipper.map

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nksoftware.library.composables.NkCardWithHeadline
import com.nksoftware.library.composables.NkCheckBoxItem
import com.nksoftware.library.composables.NkIconButton
import com.nksoftware.library.composables.NkRowNValues
import com.nksoftware.library.composables.NkSingleSelect
import com.nksoftware.library.composables.NkValueField
import com.nksoftware.skipper.R
import kotlin.text.format


@Composable
fun OsmMapOptions(mapView: OsmMap, msg: (String) -> Unit) {

   val loadError = stringResource(R.string.error_cannot_load_mbtiles_file)

   val mapLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
      if (uri != null)
         mapView.importMap(uri)
      else
         msg(loadError)
   }

   NkCardWithHeadline(
      headline = stringResource(R.string.map_type),
      icon = Outlined.Settings
   ) {
      Column {
         NkSingleSelect(
            itemList = mapView.chartTypes.keys.toList(),
            selectedItem = mapView.chart,
            set = { i -> mapView.setChartType(i) }
         )
         NkRowNValues(
            arrangement = Arrangement.SpaceBetween
         ) {
            NkCheckBoxItem(
               item = "OpenSeaMap",
               selected = mapView.openSeaMap,
               set = { mapView.toggleOpenseaMap() }
            )
            NkIconButton(
               icon = Icons.Filled.Download,
               onClick = { mapLauncher.launch("*/*") }
            )
         }
      }
   }

   NkCardWithHeadline(
      headline = stringResource(R.string.map_cache_management),
      icon = Outlined.Settings
   ) {
      NkRowNValues(
         arrangement = Arrangement.SpaceBetween
      ) {
         Row {
            NkValueField(
               modifier = Modifier
                  .width(120.dp)
                  .padding(end = 5.dp),
               label = stringResource(R.string.cache_capacity),
               value = "%,d".format(mapView.cacheCapacity)
            )

            NkValueField(
               modifier = Modifier
                  .width(120.dp)
                  .padding(end = 5.dp),
               label = stringResource(R.string.cache_usage),
               value = "%,d".format(mapView.cacheUsage)
            )
         }

         if (mapView.chart == 1) {
            NkIconButton(
               icon = Icons.Filled.Download,
               onClick = { mapView.download() }
            )
         }
      }
   }
}