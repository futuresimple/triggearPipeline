package com.futuresimple.triggear

class Request implements Serializable{
    TriggeringEvent registrationEvent
    List<String> labels = []
    List<PipelineParameters> requestedParameters = []
    List<String> changeRestrictions = []
    List<String> branchRestrictions = []
    List<String> fileRestrictions = []

    private Request(TriggeringEvent type){
        registrationEvent = type
    }

    private void addLabel(String label){
        labels.add(label)
    }

    private void addChangeRestriction(String pathPrefix){
        changeRestrictions.add(pathPrefix)
    }

    private void addBranchRestriction(String branch){
        branchRestrictions.add(branch)
    }

    private void addFileRestriction(String filePath){
        fileRestrictions.add(filePath)
    }

    private void addBranchAsParameter(){
        requestedParameters.add(PipelineParameters.BRANCH)
    }

    private void addShaAsParameter(){
        requestedParameters.add(PipelineParameters.SHA)
    }

    private void addTagAsParameter(){
        requestedParameters.add(PipelineParameters.TAG)
    }

    private void addChangesAsParameter(){
        requestedParameters.add(PipelineParameters.CHANGES)
    }

    static PushBuilder forPushes(){
        return new PushBuilder()
    }

    static TagBuilder forTags(){
        return new TagBuilder()
    }

    static LabelBuilder forLabels(){
        return new LabelBuilder()
    }

    static PrBuilder forPrOpened(){
        return new PrBuilder()
    }

    static class PushBuilder extends Builder {
        PushBuilder(){
            this.eventType = TriggeringEvent.PUSH
        }

        PushBuilder addBranchRestriction(String branch){
            getRequest().addBranchRestriction(branch)
            return this
        }

        PushBuilder addChangeRestriction(String pathPrefix){
            getRequest().addChangeRestriction(pathPrefix)
            return this
        }

        PushBuilder addChangesAsParameter(){
            getRequest().addChangesAsParameter()
            return this
        }
    }

    static class TagBuilder extends Builder {
        TagBuilder(){
            this.eventType = TriggeringEvent.TAG
        }

        TagBuilder addTagAsParameter(){
            getRequest().addTagAsParameter()
            return this
        }

        TagBuilder addBranchRestriction(String branch){
            getRequest().addBranchRestriction(branch)
            return this
        }
    }

    static class LabelBuilder extends Builder {
        LabelBuilder(){
            this.eventType = TriggeringEvent.LABEL
        }

        LabelBuilder addLabel(String label){
            getRequest().addLabel(label)
            return this
        }
    }

    static class PrBuilder extends Builder {
        PrBuilder(){
            this.eventType = TriggeringEvent.PR_OPEN
        }

        PrBuilder addBranchRestriction(String branch){
            getRequest().addBranchRestriction(branch)
            return this
        }
    }

    private static class Builder implements Serializable {
        protected TriggeringEvent eventType
        protected Request request

        protected Request getRequest(){
            if(request == null){
                request = new Request(eventType)
            }
            return request
        }

        Builder addBranchAsParameter(){
            getRequest().addBranchAsParameter()
            return this
        }

        Builder addShaAsParameter(){
            getRequest().addShaAsParameter()
            return this
        }

        Builder addFileRestriction(String filePath){
            getRequest().addFileRestriction(filePath)
            return this
        }

        Request build(){
            return getRequest()
        }
    }
}
