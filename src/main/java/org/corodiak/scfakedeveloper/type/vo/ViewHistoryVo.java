package org.corodiak.scfakedeveloper.type.vo;

import org.corodiak.scfakedeveloper.type.entity.ViewHistory;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ViewHistoryVo {

	private UserVo user;
	private WebtoonVo webtoon;

	public ViewHistoryVo(ViewHistory entity) {
		this.user = new UserVo(entity.getUser());
		this.webtoon = new WebtoonVo.WebtoonVoWithAuthor(entity.getWebtoon());
	}
}
