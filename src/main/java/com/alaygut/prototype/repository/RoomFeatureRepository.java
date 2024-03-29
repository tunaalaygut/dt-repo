package com.alaygut.prototype.repository;
 
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.RoomFeature;
import org.springframework.data.repository.CrudRepository;

public interface RoomFeatureRepository extends CrudRepository<RoomFeature, Long> {
    Iterable<RoomFeature> findAllByStateEquals(RecordState state);
    Iterable<RoomFeature> findAllByMeetingRoomSet(MeetingRoom meetingRoom);
}
