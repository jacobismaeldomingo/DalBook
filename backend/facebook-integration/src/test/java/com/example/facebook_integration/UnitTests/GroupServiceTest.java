package com.example.facebook_integration.UnitTests;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.Implementations.GroupServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GroupServiceTest {
  //Set up mocks
    @InjectMocks
   GroupServiceImpl groupService;

    @Mock
    GroupRepository groupRepository;

    @Mock
    UserRepository userRepository;

    /**
     * Test Group Creation
     */
    @Test
    public void testCreateGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("Test Description");
        userGroup.setFaculty("Test Faculty");

        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");

        when(groupRepository.save(userGroup)).thenReturn(userGroup);
        when(userRepository.findById(userGroup.getCreatorId())).thenReturn(Optional.of(user));
        UserGroup savedUserGroup = groupService.createGroup(userGroup);

        assertEquals(userGroup, savedUserGroup);
        assertEquals(userGroup.getGroupName(),"Test Group");

        //verify(groupRepository).save(userGroup);

    }

    /**
     * Test Group Creation if Group Already exists
     */
    @Test
    public void testCreateGroup_GroupAlreadyExists(){
        UserGroup existingGroup = new UserGroup();
        existingGroup.setGroupName("Test Group");
        existingGroup.setDescription("Test Description");
        existingGroup.setFaculty("Test Faculty");
        existingGroup.setId(1);

        when(groupRepository.existsById(1)).thenReturn(true);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.createGroup(existingGroup);
        });
        assertEquals("Group already exists", exception.getMessage());

    }

    /**
     * Test Group Deletion
     */
    @Test
    public void testDeleteGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("Test Description");
        userGroup.setFaculty("Test Faculty");
        userGroup.setId(1);


        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setRole(User.Role.Group_Admin);
        user.setId(1);

        userGroup.addAdmin(user);

        groupRepository.save(userGroup);



        

        when(groupRepository.findById(1)).thenReturn(Optional.of(userGroup));
        when(userRepository.findById(userGroup.getId())).thenReturn(Optional.of(user));

        //groupService.createGroup(userGroup);
        groupRepository.save(userGroup);

        String result = groupService.deleteGroup(1, 1);

        assertEquals(result, "Group successfully deleted");

       // verify(groupRepository, times(1)).delete(userGroup);
    }

    /**
     * Test Group deletion if the group is not found
     */
    @Test
    public void testDeleteGroup_GroupNotFound() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.deleteGroup(1, 1);
        });
        assertEquals(exception.getMessage(), "Group cannot be found");
    }

    /**
     * Tests adding user to Group
     */
    @Test
    public void testaddUserToGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("Test Description");
        userGroup.setFaculty("Test Faculty");
        userGroup.setId(1);

        User user = new User();
        user.setId(2);
        user.setEmail("test@dal.ca");
        user.setFirstName("Dal");
        user.setLastName("Test");
        user.setPassword("Password$99");
        user.setSecurityAnswer("Security Answer");

        userRepository.save(user);
        groupRepository.save(userGroup);
        when(groupRepository.findById(1)).thenReturn(Optional.of((userGroup)));
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(groupRepository.save(any(UserGroup.class))).thenReturn(userGroup);

       UserGroup updatedGroup = groupService.addUserToGroup(userGroup.getId(), user.getId());
        assertNotNull(updatedGroup);
        assertTrue(userGroup.getUsers().contains(user));

        //verify(userRepository).save(user);
        //verify(groupRepository).save(userGroup);

    }

    /**
     * Tests adding user to Group if group does not exist
     */
    @Test
    public void testAddUserToGroup_UserDoesNotExist() {
    UserGroup userGroup = new UserGroup();
        userGroup.setId(1);
        userGroup.setGroupName("Test Group");


    when(userRepository.findById(2)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.addUserToGroup(1, 2);
    });
    assertEquals("User cannot be found", exception.getMessage());



    }

    /**
     * Test Deleting User From Group
     */
    @Test
    public void testDeleteUserFromGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("Test Description");
        userGroup.setFaculty("Test Faculty");
        userGroup.setId(1);
        User user = new User();
        user.setId(2);
        user.setEmail("test@dal.ca");
        user.setFirstName("Dal");
        user.setLastName("Test");
        user.setPassword("Password$99");
        user.setSecurityAnswer("Security Answer");
        userRepository.save(user);
        groupRepository.save(userGroup);
        when(groupRepository.findById(1)).thenReturn(Optional.of(userGroup));
        when(userRepository.findById(2)).thenReturn(Optional.of(user));


        when(userRepository.save(any(User.class))).thenReturn(user);
        when(groupRepository.save(any(UserGroup.class))).thenReturn(userGroup);

        UserGroup updatedGroup = groupService.addUserToGroup(userGroup.getId(), user.getId());
        assertTrue(userGroup.getUsers().contains(user));
        String response =groupService.deleteUser(1, 2);
        assertEquals("User successfully removed", response);




    }

    /**
     * Tests Deleting User From Group If Group Does Not Exist
     */
    @Test
    public void testDeleteUserFromGroup_UserDoesNotExist() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("Test Description");
        userGroup.setFaculty("Test Faculty");
        userGroup.setId(1);
        User user = new User();
        user.setId(2);
        user.setEmail("test@dal.ca");
        user.setFirstName("Dal");
        user.setLastName("Test");
        user.setPassword("Password$99");
        user.setSecurityAnswer("Security Answer");
        userRepository.save(user);
        groupRepository.save(userGroup);
        when(groupRepository.findById(1)).thenReturn(Optional.of(userGroup));
        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.deleteUser(1, 2);
        });
        assertEquals("User is not in this group", exception.getMessage());
    }





}
