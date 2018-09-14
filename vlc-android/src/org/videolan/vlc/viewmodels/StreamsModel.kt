/*******************************************************************************
 *  MRLPanelModel.kt
 * ****************************************************************************
 * Copyright © 2018 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 ******************************************************************************/

package org.videolan.vlc.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.videolan.medialibrary.media.MediaWrapper
import org.videolan.vlc.VLCApplication


class StreamsModel: ScopedModel() {
     val observableSearchText = ObservableField<String>()
     val observableHistory = MutableLiveData<Array<MediaWrapper>>()

    fun updateHistory() = launch {
        val history = withContext(Dispatchers.IO) { VLCApplication.getMLInstance().lastStreamsPlayed() }
        observableHistory.value = history
    }

    fun rename(position: Int, name: String) {
        val media = observableHistory.value?.get(position) ?: return
        media.rename(name)
        updateHistory()
    }
}