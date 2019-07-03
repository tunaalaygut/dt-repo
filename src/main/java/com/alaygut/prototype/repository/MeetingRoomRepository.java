package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.MeetingRoom;

public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Long> {
}
