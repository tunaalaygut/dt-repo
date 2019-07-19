package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Login;  
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private RoleService roleService;
    private LoginService loginService;

    private PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, LoginService loginService) {
        this.memberRepository = memberRepository;
        this.loginService = loginService;
    }

    /**
     * Database'e formdan alınan bilgiler ile kullanıcı ekleme
     * @param form member DTO
     */

    @Override
    @Transactional(readOnly = false)
    public void addMember(AddMemberForm form) {
        Role role = roleService.getRole(form.getRoleId());
        System.out.println(form.getPassword());
        Login login = new Login(
                form.getUsername(),
                passwordEncoder.encode(form.getPassword())
        );
        Member member = new Member(
                form.getFirstName(),
                form.getLastName(),
                form.getEmail(),
                form.getPhone(),
                role,
                login
        );
        loginService.addLogin(login);
        if (form.getCreatorId() != null)
            member.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));
        memberRepository.save(member);
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    /**
     * Stateleri Aktif(1) olan üyeleri döndürür
     */

    @Override
    public Iterable<Member> getAllActiveMembers() {
    	return memberRepository.findByStateEquals(RecordState.ACTIVE);
    }
    
    /**
     * Spesifik bir üye döndürür
     * @param memberId üyenin unique id'si    
     */

    @Override
    public Member getMember(Long memberId) {
    	return memberRepository.findById(memberId).orElse(null);
    }
    
    /**
     * State'i Aktiften(1) Deaktife(0) alır
     * @param idTransfer id transfer objesi
     */

    @Override
    @Transactional(readOnly = false)
    public void deactivate(IDTransfer idTransfer) {
    	Member member = memberRepository.findById(idTransfer.getRecordId()).orElse(null);
    	member.setState(RecordState.NONACTIVE);
    	memberRepository.save(member);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void edit(AddMemberForm addMemberForm) {

    	Member member = memberRepository.findById(addMemberForm.getRecordId()).orElse(null);
    	Role role = roleService.getRole(addMemberForm.getRoleId());
    	Login login = member.getLogin();
    	member.setFirstName(addMemberForm.getFirstName());
    	member.setLastName(addMemberForm.getLastName());
        member.setPhone(addMemberForm.getPhone());
        member.setRole(role);

        if (!addMemberForm.getNewUsername().equals(member.getUsername()))
            login.setUsername(addMemberForm.getNewUsername());

        if (!addMemberForm.getNewEmail().equals(member.getEmail()) )
            member.setEmail(addMemberForm.getNewEmail());

        member.setUpdater(memberRepository.findById(addMemberForm.getUpdaterId()).orElse(null));
        if (addMemberForm.getCreatorId() != null)
            member.setCreator(memberRepository.findById(addMemberForm.getCreatorId()).orElse(null));
    	 	
    	loginService.addLogin(login);
    	memberRepository.save(member);
    }
    
    /**
     * Üyenin profil sayfasından edit yapmasını sağlar
     * @param addMemberForm üye DTO'su
     */
    @Override
    @Transactional(readOnly = false)
    public void profileEdit(AddMemberForm addMemberForm) {
    	Member member = memberRepository.findById(addMemberForm.getRecordId()).orElse(null);
    	Login login = member.getLogin();
    	member.setFirstName(addMemberForm.getFirstName());
    	member.setLastName(addMemberForm.getLastName());
    	member.setPhone(addMemberForm.getPhone());
    	
        if (!addMemberForm.getNewEmail().equals(member.getEmail()) )
            member.setEmail(addMemberForm.getNewEmail());
    	
        member.setUpdater(memberRepository.findById(addMemberForm.getUpdaterId()).orElse(null));
        if (addMemberForm.getCreatorId() != null)
            member.setCreator(memberRepository.findById(addMemberForm.getCreatorId()).orElse(null));
    	
    		
    	loginService.addLogin(login);
    	memberRepository.save(member);
    }


    @Override
    public AddMemberForm getAddMemberForm() {
        AddMemberForm addMemberForm = new AddMemberForm();

        addMemberForm.setAllRoles(roleService.getAllActiveRoles());

        return addMemberForm;
    }

    /**
     * Hatalı girişlerde formu düzeltir
     */
    @Override
    public void fixForm(AddMemberForm addMemberForm) {
        addMemberForm.setAllRoles(roleService.getAllActiveRoles());
        if(addMemberForm.getRecordId() != null){
            addMemberForm.setEmail(this.getMember(addMemberForm.getRecordId()).getEmail());
            addMemberForm.setUsername(this.getMember(addMemberForm.getRecordId()).getUsername());
        }
    }

    /**
     * Unique datalar için error mesajları
     */
    @Override
    public void addErrors(AddMemberForm form, BindingResult bindingResult) {
        if (this.usernameExists(form.getNewUsername()) && !(form.getNewUsername().equals(form.getOriginalUsername())))
            bindingResult.addError(new FieldError("addMemberForm", "username", "Kullanıcı adı mevcut."));
        if (this.emailExists(form.getNewEmail()) && !(form.getNewEmail().equals(form.getOriginalEmail())))
            bindingResult.addError(new FieldError("addMemberForm", "email", "E-mail mevcut."));
    }

    /**
     * Username'in unique olması için kontrol yapar
     * @param username Kullanıcı adı
     */
    @Override
    public boolean usernameExists(String username) {
        return memberRepository.existsByLoginUsername(username);
    }

    /**
     * Emailın unique olması için kontrol yapar
     * @param email Üyenin e-mail adresi
     */
    @Override
    public boolean emailExists(String email){
        return memberRepository.existsByEmail(email);
    }

    /**
     * Editlenecek üyenin formunu dolu halde getirir
     * @param username editlenen üyenin Id'si
     * @return dolu member DTO'su
     */
    @Override
    public Member getMember(String username) {
        return memberRepository.findByLoginUsername(username);
    }

    @Override
    public AddMemberForm getEditForm(Long memberId) {
        Member member = getMember(memberId);
        AddMemberForm addMemberForm = new AddMemberForm();
        addMemberForm.setRecordId(member.getMemberId());
        addMemberForm.setFirstName(member.getFirstName());
        addMemberForm.setLastName(member.getLastName());
        addMemberForm.setEmail(member.getEmail());
        addMemberForm.setPhone(member.getPhone());
        addMemberForm.setUsername(member.getUsername());
        addMemberForm.setRoleId(member.getRole().getRoleId());
       
        addMemberForm.setAllRoles(roleService.getAllActiveRoles());
        
        return addMemberForm;
    }

    /**
     * Kullanıcı adını baz alarak üye aratır
     * @param username Kullanıcı adı
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByLoginUsername(username);

        if (member == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        return member;
    }

    /**
     * E-mailı baz alarak üye aratır
     * @param email Üyenin e-mai adresi
     * @return member Email ile bulunan kullanici
     */
    public Member LoadUserEmail(String email) {
        Member member = this.memberRepository.findByEmail(email);
        return member;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
