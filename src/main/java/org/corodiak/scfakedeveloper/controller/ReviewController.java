package org.corodiak.scfakedeveloper.controller;

import java.util.List;

import org.corodiak.scfakedeveloper.auth.util.AuthUtil;
import org.corodiak.scfakedeveloper.service.ReviewService;
import org.corodiak.scfakedeveloper.type.dto.ResponseModel;
import org.corodiak.scfakedeveloper.type.dto.ReviewDto;
import org.corodiak.scfakedeveloper.type.vo.ReviewVo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;

	@Secured({"ROLE_USER"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseModel reviewAdd(
		@RequestBody ReviewDto reviewDto
	) {
		Long userSeq = AuthUtil.getAuthenticationInfoSeq();
		reviewDto.setUser(userSeq);
		reviewService.addReview(reviewDto);
		ResponseModel responseModel = ResponseModel.builder().build();
		return responseModel;
	}

	// Permit All
	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public ResponseModel reviewGet(
		@PathVariable("seq") Long seq
	) {
		ReviewVo review = reviewService.findReview(seq);
		ResponseModel responseModel = ResponseModel.builder().build();
		responseModel.addData("review", review);
		return responseModel;
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/myReview", method = RequestMethod.GET)
	public ResponseModel myReviewList(
		@RequestParam(name = "start", required = false, defaultValue = "0") long start,
		@RequestParam(name = "display", required = false, defaultValue = "20") long display
	) {
		Long userSeq = AuthUtil.getAuthenticationInfoSeq();
		List<ReviewVo> reviewList = reviewService.findByUserSeq(userSeq, start, display);
		ResponseModel responseModel = ResponseModel.builder().build();
		responseModel.addData("reviewList", reviewList);
		return responseModel;
	}

	// Permit All
	@RequestMapping(value = "/webtoonReview/{seq}", method = RequestMethod.GET)
	public ResponseModel webtoonReviewList(
		@PathVariable("seq") Long seq,
		@RequestParam(name = "start", required = false, defaultValue = "0") long start,
		@RequestParam(name = "display", required = false, defaultValue = "20") long display
	) {
		List<ReviewVo> reviewList = reviewService.findByWebtoonSeq(seq, start, display);
		ResponseModel responseModel = ResponseModel.builder().build();
		responseModel.addData("reviewList", reviewList);
		return responseModel;
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseModel reviewUpdate(
		@RequestBody ReviewDto reviewDto
	) {
		reviewService.updateReview(reviewDto);
		ResponseModel responseModel = ResponseModel.builder().build();
		return responseModel;
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public ResponseModel reviewDelete(
		@PathVariable("seq") Long seq
	) {
		reviewService.removeReview(seq);
		ResponseModel responseModel = ResponseModel.builder().build();
		return responseModel;
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/like/{seq}", method = RequestMethod.POST)
	public ResponseModel likReview(
		@PathVariable("seq") Long reviewSeq
	) {
		Long userSeq = AuthUtil.getAuthenticationInfoSeq();
		reviewService.likeReview(userSeq, reviewSeq);
		ResponseModel responseModel = ResponseModel.builder().build();
		return responseModel;
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/dislike/{seq}", method = RequestMethod.POST)
	public ResponseModel dislikReview(
		@PathVariable("seq") Long reviewSeq
	) {
		Long userSeq = AuthUtil.getAuthenticationInfoSeq();
		reviewService.dislikeReview(userSeq, reviewSeq);
		ResponseModel responseModel = ResponseModel.builder().build();
		return responseModel;
	}
}
