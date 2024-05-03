package org.example.pnubookstore.service.impl;

import java.util.List;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.dto.AuctionBoardDto;
import org.example.pnubookstore.dto.AuctionDetailDto;
import org.example.pnubookstore.repository.AuctionJpaRepository;
import org.example.pnubookstore.service.AuctionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
	private final AuctionJpaRepository auctionJpaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<AuctionBoardDto> getAuctionBoard(Pageable pageable) {
		return auctionJpaRepository.findAllPaginated(pageable)
			.map(auction -> new AuctionBoardDto(auction.getId(), auction.getTitle()))
			.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionDetailDto getAuctionDetail(Long auctionId) {
		return AuctionDetailDto.of(auctionJpaRepository.findByIdFetchJoin(auctionId)
			.orElseThrow( () -> new Exception404(BaseExceptionStatus.AUCTION_NOT_FOUND)));
	}
}
