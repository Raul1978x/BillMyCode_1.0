package com.BillMyCode.app.controllers;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.orElse(null);
    }

    @PostMapping
    public Comment createComment(@RequestBody String comentario) {
        return commentService.createComment(comentario);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody String comentario) {
        return commentService.updateComment(id, comentario);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteCommentById(id);
    }
}
