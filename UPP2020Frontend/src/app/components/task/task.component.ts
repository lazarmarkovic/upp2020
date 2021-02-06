import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {HttpEventType, HttpResponse} from '@angular/common/http';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  public processId: string | undefined;
  public formDTO: any;

  public regUserForm = new FormGroup({});
  public genres: any[] | undefined;
  public betaReaders: any[] | undefined;
  public enumValues: any[] | undefined;

  public selectedFiles = [];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private taskService: TaskService,
              private tService: ToastrService) {}

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.processId = String(params.id);
      }
    );

    this.buildForm();
  }

  buildForm(): void {
    this.taskService
      .getTaskForm(this.processId)
      .subscribe(
        response => {
          this.formDTO = response;
          console.log('Get task form response:');
          console.log(response);

          this.formDTO.formFields.forEach((field: any) => {
            // drugi parametar u form control bi trebao da bude validator
            // @ts-ignore
            this.regUserForm.addControl(field.id, new FormControl(field.value.value, []));

            // @ts-ignore
            if (field.type.name === 'multiselectGenre'  ) {
              // @ts-ignore
              this.genres = Object.keys(field.type.values);
            }

            // @ts-ignore
            if (field.type.name === 'multiselectBeta'  ) {
              // @ts-ignore
              this.betaReaders = Object.keys(field.type.values);
            }

            if (field.type.name === 'selectOneGenre'  ) {
              // @ts-ignore
              this.genres = Object.keys(field.type.values);
            }

            if (field.type.name === 'enum') {
              this.enumValues = Object.keys(field.type.values);
            }

            console.log(response.readonlyFields);
          });
        },
        err => {
          console.log('Error while building the form: ');
          console.log(err);
        }
      );
  }

  onSubmit(): void {
    console.log('Submitted form: ');
    console.log(JSON.stringify(this.regUserForm.value));

    const values: { name: string | number; value: any; }[] = [];
    // tslint:disable-next-line:forin
    for (const p in this.regUserForm.value) {
      console.log(this.regUserForm.value);
      values.push({ name: p, value: this.regUserForm.value[p] });
    }

    // @ts-ignore
    const {taskId} = this.formDTO;
    this.taskService
      .submit(values, taskId, this.formDTO.formName)
      .subscribe(
        res => {
          console.log(res);
          this.tService.success('Form "' + this.formDTO.formName + '" is submitted.');
          this.router.navigate(['/tasks']);
        },
        err => {
          alert('Error while submitting form:');
          console.log(err);
        }
      );
  }

  uploadFiles(event: any): void {
    this.taskService.uploadFiles(this.formDTO.taskId, event.target.files).subscribe(
      response => {
          console.log('UPLAODED');
          this.tService.success('All files are uploaded', 'Success');
      },
      err => {
        console.log(err);
      });
  }
}
