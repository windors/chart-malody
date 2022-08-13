package cn.windors.malody.util;

import cn.windors.malody.dto.ActivityChartDto;
import cn.windors.malody.dto.ChartDto;
import cn.windors.malody.dto.EventDto;
import cn.windors.malody.dto.SongDto;
import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Event;
import cn.windors.malody.entity.core.Song;
import cn.windors.malody.properties.MalodyCoreProperties;

import java.time.temporal.ChronoField;

/**
 * @author Windor
 */
public class DtoStaticFactory {
    public static ChartDto getChartDtoInstance(Chart chart) {
        return new ChartDto(
                chart.getId(),
                chart.getUid(),
                chart.getCreator(),
                chart.getVersion(),
                chart.getLevel(),
                chart.getLevel(),
                chart.getExamineState().getValue(),
                chart.getSize(),
                chart.getMode().getValue()
        );
    }

    public static EventDto getEventDtoInstance(Event event) {
        return new EventDto(event.getId(), event.getName(), event.getSponsor(), event.getStart(), event.getEnd(), MalodyCoreProperties.fileDownloadAddress + event.getCover(), true);
    }

    public static SongDto getSongDtoInstance(Song song, String downloadAddress) {
        return new SongDto(song.getId(),
                downloadAddress + song.getCoverUri(),
                song.getLength(), song.getBpm(), song.getTitle(),
                song.getArtist(), song.getMode(), song.getUpdateTime().getLong(ChronoField.MILLI_OF_SECOND));
    }

    public static SongDto getSongDtoOriginTitleInstance(Song song, String downloadAddress) {
        return new SongDto(song.getId(),
                downloadAddress + song.getCoverUri(),
                song.getLength(), song.getBpm(), song.getOriginTitle(),
                song.getArtist(), song.getMode(), song.getUpdateTime().getLong(ChronoField.MILLI_OF_SECOND));
    }

    public static ActivityChartDto getInstance(Chart chart, Song song) {
        return new ActivityChartDto(chart.getSid(), chart.getId(), chart.getUid(), chart.getCreator(), song.getTitle(),
                song.getArtist(), chart.getVersion(), chart.getLevel(), chart.getLength(), chart.getExamineState().getValue(),
                song.getCoverUri(), 0L, chart.getMode().getValue());
    }
}
