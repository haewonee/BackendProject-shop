package com.apple.shop.comment;

import com.apple.shop.item.Item;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import com.apple.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    public void saveComment(String content,Long parentId, Authentication auth){
            CustomUser user = (CustomUser) auth.getPrincipal();
            var data = new Comment();
            data.setContent(content);
//            data.setUsername(user.getUsername());
        Member member = memberRepository.findById(user.id).get();
            data.setMember(member);
        data.setParentId(parentId);

            commentRepository.save(data);
    }

    public List<Comment> getComment(Long id){
        List<Comment> commentList = commentRepository.findAllByParentId(id);
        return commentList;
    }


}
