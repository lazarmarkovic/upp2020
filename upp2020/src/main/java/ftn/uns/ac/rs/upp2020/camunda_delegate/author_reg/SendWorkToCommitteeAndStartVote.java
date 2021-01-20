package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SendWorkToCommitteeAndStartVote implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> c_members = userRepository.findAllByRole(Role.COMMITTEE_MEMBER);
        List<User> c_president = userRepository.findAllByRole(Role.COMMITTEE_PRESIDENT);

        List<User> committee = Stream.concat(c_members.stream(), c_president.stream())
                .collect(Collectors.toList());

        List<String> committeeUsernames =
                committee
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        delegateExecution.setVariable("committee", committee);
        delegateExecution.setVariable("committeeSize", committee.size());
        delegateExecution.setVariable("committeeUsernames", committeeUsernames);

        delegateExecution.setVariable("votingRoundCount",0);
    }
}
