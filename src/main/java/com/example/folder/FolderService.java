package com.example.folder;

import com.example.auth.CurrentUser;
import com.example.exceptions.ForbiddenException;
import com.example.exceptions.NotFoundException;
import com.example.note.Note;
import com.example.note.NoteSpecs;
import com.example.user.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final CurrentUser currentUser;

    private Specification<Folder> ownedFolder(Long id, User user) {
        return Specification
                .allOf(FolderSpecs.withId(id))
                .and(FolderSpecs.belongsTo(user));
    }

    public FolderService(FolderRepository folderRepository, CurrentUser currentUser) {
        this.folderRepository = folderRepository;
        this.currentUser = currentUser;
    }

    public FolderResponse create(String name, Authentication auth) {
        User user = currentUser.get(auth);
        Folder folder = new Folder(name, user);
        Instant now = Instant.now();
        folder.setCreatedAt(now);
        folder.setUpdatedAt(now);
        folderRepository.save(folder);
        return FolderResponse.fromEntity(folder);
    }

    // No pagination on folders
    public List<FolderResponse> getMyFolders(Authentication auth) {
        User user = currentUser.get(auth);
        return folderRepository.findByOwner(user)
                .stream()
                .map(FolderResponse::fromEntity)
                .toList();
    }

    public FolderResponse getById(Long id, Authentication auth) {
        User user = currentUser.get(auth);

        Specification<Folder> spec = ownedFolder(id, user);

        Folder folder = folderRepository.findOne(spec)
                .orElseThrow(() -> new NotFoundException("Folder not found"));

        return FolderResponse.fromEntity(folder);
    }

    public FolderResponse update(Long id, String name, Authentication auth) {
        User user = currentUser.get(auth);

        Specification<Folder> spec = ownedFolder(id, user);

        Folder folder = folderRepository.findOne(spec)
                .orElseThrow(() -> new NotFoundException("Folder not found"));

        folder.setName(name);
        folderRepository.save(folder);
        return FolderResponse.fromEntity(folder);
    }

    public void delete(Long id, Authentication auth) {
        User user = currentUser.get(auth);
        Specification<Folder> spec = ownedFolder(id, user);

        Folder folder = folderRepository.findOne(spec)
                .orElseThrow(() -> new NotFoundException("Folder not found"));

        folderRepository.deleteById(id);
    }
}
