package com.togedog.friend.mapper;

import com.togedog.friend.dto.Dto;
import com.togedog.friend.entity.Friend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    Dto.Response friendToResponse(Friend friend);
}
