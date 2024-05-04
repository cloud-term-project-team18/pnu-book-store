package org.example.pnubookstore.service;

import java.util.List;

import org.example.pnubookstore.dto.AuctionBoardDto;
import org.example.pnubookstore.dto.AuctionDetailDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AuctionService {

	List<AuctionBoardDto> getAuctionBoard(Pageable pageable);
	AuctionDetailDto getAuctionDetail(Long auctionId);
}
