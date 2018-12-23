package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This controller method is called when the request url is '/image/{imageId}/{imageTitle}/comments' and also the incoming request is of POST type
    //The method calls the createComment() method in the business logic passing the id of the image for which the comments is being added and comment
    //Looks for a controller method with request mapping of type '/images/{id}/{title}'
    @RequestMapping(value = "/images/{id}/{title}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("id") Integer id, @RequestParam("comment") String imageComment, HttpSession session){
        User user = (User) session.getAttribute("loggeduser");
        Image image = imageService.getImage(id);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setImage(image);
        comment.setCreatedDate(new Date());
        comment.setText(imageComment);

        commentService.addComment(comment);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
