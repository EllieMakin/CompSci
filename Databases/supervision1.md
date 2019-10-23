# Supervision 1 Questions

4.

```
// Represents the 'experiment' entity.
Experiments
    experiment_id
    name
    description

// Represents the 'run' entity.
Runs
    run_id
    experiment_id

// Represents the set of inputs passed to a run.
Input_sets
    input_set_id
    parameter_name
    parameter_value

// Represents hte set of outputs returned by a run
Output_sets
    output_set_id
    parameter_name
    parameter_value

// Represents the inputs required by each type of experiment
Needs_input_of
    experiment_id
    input_set_id

// Represents the set of outputs that will be returned by each type of experiment.
Gives_output_of
    experiment_id
    output_set_id

// Represents the exact parameters received by a particular run.
Received_input
    run_id
    input_set_id

// Represents the exact parameters returned by particular run.
Returned_output
    run_id
    output_set_id
```

5.

```
select i.grid_width, i.grid_height, avg(i.message_count), avg(i.run_time)
    from runs as r
        join received_input as ri on ri.run_id = r.run_id
        join input_sets as i on i.input_set_id = ri.input_set_id
    group by i.grid_width, i.grid_height
```
