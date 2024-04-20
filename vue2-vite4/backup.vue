addSecondComment( firstComment,comment) {
    console.log(this.secondCommentContent)
    let secondComment = {}
    secondComment.articleId = this.articleId
    secondComment.userId = this.currentUser.id
    secondComment.commentContent = this.secondCommentContent
    if (comment.parentId === null) {
      // 说明是comment是一级评论
      secondComment.parentId = comment.id

    } else {
      // 说明是comment的二级评论，也就是对二级评论进行评论
      secondComment.parentId = comment.parentId
    }
    secondComment.replyUserId = comment.userId
    secondComment.replyCommentId = comment.id
    this.request.post("/comment/addComment", secondComment).then(res => {
      if (res.code === '200') {
        this.$message.success("评论成功")
        // 加一再减一,不然滚动加载出错哦
        if (comment.parentId === null) {
          // 说明是comment是一级评论
          if (comment.replyTotalCount >= 3) {
            comment.children.pop()
          }
          comment.children.unshift(res.data)
          comment.replyTotalCount += 1

        } else {
          // 说明comment是二级评论，也就是对二级评论进行评论，也是二级评论
          if (firstComment.replyTotalCount >= 3) {
            comment.children.pop()
          }
          firstComment.children.unshift(res.data)

          firstComment.replyTotalCount += 1
        }
        this.childrenCommentContent = ''
      }
    })
  },